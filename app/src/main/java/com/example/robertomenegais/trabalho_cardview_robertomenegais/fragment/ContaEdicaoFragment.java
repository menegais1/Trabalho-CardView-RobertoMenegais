package com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.robertomenegais.trabalho_cardview_robertomenegais.R;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.activity.ContaActivity;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.model.Conta;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.service.ContaServiceBD;

/**
 * Created by Roberto Menegais on 29/01/2017.
 */

public class ContaEdicaoFragment extends BaseFragment {

    private Conta conta;
    private RadioButton rbCorrente, rbPoupanca, rbSalario;
    private EditText nome, sobrenome, cpf, limite, saldo;
    private ImageView foto;
    private final String SAVE = "save";
    private final String DELETE = "delete";
    private ContaServiceBD contaServiceBD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        ((ContaActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_fragment_edicaoconta));

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edicaoconta, container, false);
        foto = (ImageView) view.findViewById(R.id.imageview_card_fragmentedicao);

        if (conta.urlFoto != null) {
            foto.setImageURI(Uri.parse(conta.urlFoto));
        }
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });

        rbCorrente = (RadioButton) view.findViewById(R.id.radiobutton_fragmentedicao_corrente);
        rbPoupanca = (RadioButton) view.findViewById(R.id.radiobutton_fragmentedicao_poupanca);
        rbSalario = (RadioButton) view.findViewById(R.id.radiobutton_fragmentedicao_salario);

        if (conta.tipo.equals(getContext().getResources().getString(R.string.tipo_corrente))) {

            rbCorrente.setChecked(true);
        } else if (conta.tipo.equals(getContext().getResources().getString(R.string.tipo_poupanca))) {

            rbPoupanca.setChecked(true);
        } else if (conta.tipo.equals(getContext().getResources().getString(R.string.tipo_salario))) {

            rbSalario.setChecked(true);
        }

        nome = (EditText) view.findViewById(R.id.edittext_nome_fragmentedicao);
        sobrenome = (EditText) view.findViewById(R.id.edittext_sobrenome_fragmentedicao);
        limite = (EditText) view.findViewById(R.id.edittext_limite_fragmentedicao);
        saldo = (EditText) view.findViewById(R.id.edittext_saldo_fragmentedicao);
        cpf = (EditText) view.findViewById(R.id.edittext_cpf_fragmentedicao);

        nome.setText(conta.nome);
        sobrenome.setText(conta.sobrenome);
        cpf.setText(conta.cpf);
        limite.setText(conta.limite.toString());
        saldo.setText(conta.saldo.toString());

        contaServiceBD = ContaServiceBD.getInstance(getContext());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Uri arquivoUri = data.getData();
            if (arquivoUri.toString().contains("images")) {
                foto.setImageURI(arquivoUri);
                conta.urlFoto = arquivoUri.toString();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment_edicaoconta, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_salvar:
                conta.nome = nome.getText().toString();
                conta.sobrenome = sobrenome.getText().toString();
                conta.saldo = Double.parseDouble(saldo.getText().toString());
                conta.limite = Double.parseDouble(limite.getText().toString());
                conta.cpf = cpf.getText().toString();

                if (rbCorrente.isChecked()) {
                    conta.tipo = getContext().getResources().getString(R.string.tipo_corrente);
                } else if (rbPoupanca.isChecked()) {
                    conta.tipo = getContext().getResources().getString(R.string.tipo_poupanca);
                } else if (rbSalario.isChecked()) {
                    conta.tipo = getContext().getResources().getString(R.string.tipo_salario);
                }

                new CarrosTask().execute(SAVE);

                Toast.makeText(getContext(), "Salvo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuitem_excluir:

                new CarrosTask().execute(DELETE);

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

    private class CarrosTask extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... strings) {

            if (strings[0].equals("save")) {
                return contaServiceBD.salvar(conta);
            } else if (strings[0].equals("delete")) {
                return contaServiceBD.excluir(conta);
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if (aLong > 0) {

                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_realizadacomsucesso);

            }else{
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_erroaorealizaroperacao);

            }
        }
    }
}
