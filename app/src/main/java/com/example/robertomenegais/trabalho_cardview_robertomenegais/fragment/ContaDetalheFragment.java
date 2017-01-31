package com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.robertomenegais.trabalho_cardview_robertomenegais.R;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.activity.ContaActivity;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.model.Conta;

/**
 * Created by Roberto Menegais on 29/01/2017.
 */

public class ContaDetalheFragment extends BaseFragment {


    private Conta conta;
    private RadioButton rbCorrente, rbPoupanca, rbSalario;
    private TextView nome, cpf, limite, saldo;
    private ImageView foto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        ((ContaActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_fragment_detalheconta));

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalheconta, container, false);

        foto = (ImageView) view.findViewById(R.id.imageview_card_fragmentdetalhe);

        if (conta.urlFoto != null) {
            foto.setImageURI(Uri.parse(conta.urlFoto));
        }

        rbCorrente = (RadioButton) view.findViewById(R.id.radiobutton_fragmentdetalhe_corrente);
        rbPoupanca = (RadioButton) view.findViewById(R.id.radiobutton_fragmentdetalhe_poupanca);
        rbSalario = (RadioButton) view.findViewById(R.id.radiobutton_fragmentdetalhe_salario);

        if (conta.tipo.equals(getContext().getResources().getString(R.string.tipo_corrente))) {

            rbCorrente.setChecked(true);
        } else if (conta.tipo.equals(getContext().getResources().getString(R.string.tipo_poupanca))) {

            rbPoupanca.setChecked(true);
        } else if (conta.tipo.equals(getContext().getResources().getString(R.string.tipo_salario))) {

            rbSalario.setChecked(true);
        }

        nome = (TextView) view.findViewById(R.id.textview_card_fragmentdetalhe);
        limite = (TextView) view.findViewById(R.id.textview_fragmentdetalhe_limite);
        saldo = (TextView) view.findViewById(R.id.textview_fragmentdetalhe_saldo);
        cpf = (TextView) view.findViewById(R.id.textview_fragmentdetalhe_cpf);

        nome.setText(conta.nome + " " + conta.sobrenome);
        cpf.setText(conta.cpf);
        limite.setText(conta.limite.toString());
        saldo.setText(conta.saldo.toString());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment_detalheconta, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_editar:
                ContaEdicaoFragment contaEdicaoFragment = new ContaEdicaoFragment();
                Log.d(TAG, conta.toString());
                replaceFragment(R.id.fragment_container, contaEdicaoFragment);
                contaEdicaoFragment.setConta(conta);
                break;
            case android.R.id.home:
                getActivity().finish();
                break;

        }
        return true;
    }

    public void setConta(Conta conta) {

        this.conta = conta;
    }
}
