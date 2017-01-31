package com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robertomenegais.trabalho_cardview_robertomenegais.R;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.activity.ContaActivity;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.activity.ContasActivity;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.adapter.ContasAdapter;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.model.Conta;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.service.ContaServiceBD;

import java.util.ArrayList;
import java.util.List;


public class ContasFragment extends BaseFragment implements SearchView.OnQueryTextListener {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ContaServiceBD contaServiceBD;
    private String tipo;
    private List<Conta> contas;
    private Conta conta;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        ((ContasActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragmentcontas);

        if (getArguments() != null) {
            this.tipo = getArguments().getString("tipo");
        }
        contaServiceBD = ContaServiceBD.getInstance(getActivity());

        //contaServiceBD = new ContaServiceBD(getContext());

    }

    @Override
    public void onResume() {
        super.onResume();

        new Task().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contas, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_fragmentcontas);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_fragmentcontas);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Task().execute();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,R.color.refresh_progress_2,R.color.refresh_progress_3);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_fragment_contas, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_pesquisar));

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Conta> contaList = new ArrayList<>();
        for (Conta conta : contas) {
            if (conta.nome.contains(newText)) {
                contaList.add(conta);
            }

            //atualizar o adapter
            ContasAdapter adapter = new ContasAdapter(getContext(),contaList,contaOnClickListener());
            recyclerView.setAdapter(adapter);

        }
        return true;

    }


        protected ContasAdapter.ContaOnClickListener contaOnClickListener(){
            return new ContasAdapter.ContaOnClickListener() {

                @Override
                public void onClickConta(View view, int idx) {
                    conta = contas.get(idx);
                    Intent intent = new Intent(getContext(), ContaActivity.class);
                    intent.putExtra("qualFragmentAbrir","ContaDetalheFragment");
                    intent.putExtra("Conta",conta);
                    startActivity(intent);
                }
            };


    }


        private class Task extends AsyncTask<Void, Void, List<Conta>> {


            @Override
            protected List<Conta> doInBackground(Void... voids) {

                if (ContasFragment.this.tipo.equals(getString(R.string.tipo_corrente))) {
                    return contaServiceBD.getByTipo(getString(R.string.tipo_corrente));
                } else if (ContasFragment.this.tipo.equals(getString(R.string.tipo_poupanca))) {
                    return contaServiceBD.getByTipo(getString(R.string.tipo_poupanca));
                } else if (ContasFragment.this.tipo.equals(getString(R.string.tipo_salario))) {
                    return contaServiceBD.getByTipo(getString(R.string.tipo_salario));

                } else if (ContasFragment.this.tipo.equals(getString(R.string.tipo_todos))) {
                    return contaServiceBD.getAll();

                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Conta> contas) {
                super.onPostExecute(contas);

                ContasFragment.this.contas = contas;

                //colocar adapter e setar na recyclerView
                ContasAdapter adapter = new ContasAdapter(getContext(),contas,contaOnClickListener());
                recyclerView.setAdapter(adapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        }

}
