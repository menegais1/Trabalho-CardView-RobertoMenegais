package com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment;

import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class ContaNovoFragment extends BaseFragment {


    private RadioButton rbCorrente, rbPoupanca, rbSalario;
    private EditText nome, sobrenome, cpf, limite, saldo;
    private ImageView foto;
    private Conta conta;
    private ContaServiceBD contaServiceBD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        ((ContaActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_novoconta);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novoconta, container, false);
        conta = new Conta();
        foto = (ImageView) view.findViewById(R.id.imageview_card_fragmentnovoconta);


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });

        rbCorrente = (RadioButton) view.findViewById(R.id.radiobutton_fragmentnovoconta_corrente);
        rbPoupanca = (RadioButton) view.findViewById(R.id.radiobutton_fragmentnovoconta_poupanca);
        rbSalario = (RadioButton) view.findViewById(R.id.radiobutton_fragmentnovoconta_salario);


        nome = (EditText) view.findViewById(R.id.edittext_nome_fragmentnovoconta);
        sobrenome = (EditText) view.findViewById(R.id.edittext_sobrenome_fragmentnovoconta);
        limite = (EditText) view.findViewById(R.id.edittext_limite_fragmentnovoconta);
        saldo = (EditText) view.findViewById(R.id.edittext_saldo_fragmentnovoconta);
        cpf = (EditText) view.findViewById(R.id.edittext_cpf_fragmentnovoconta);


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
        inflater.inflate(R.menu.menu_fragment_novoconta, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.menuitem_salvar:
                conta = new Conta();
                conta.nome = nome.getText().toString();
                conta.sobrenome = sobrenome.getText().toString();
                conta.limite = Double.parseDouble(limite.getText().toString());
                conta.saldo = Double.parseDouble(saldo.getText().toString());
                conta.cpf = cpf.getText().toString();

                if (rbCorrente.isChecked()) {
                    conta.tipo = getContext().getResources().getString(R.string.tipo_corrente);
                } else if (rbPoupanca.isChecked()) {
                    conta.tipo = getContext().getResources().getString(R.string.tipo_poupanca);
                } else if (rbSalario.isChecked()) {
                    conta.tipo = getContext().getResources().getString(R.string.tipo_salario);
                }
                new CarrosTask().execute();
                // Toast.makeText(getContext(), "salvando", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                getActivity().finish();
                break;
        }


        return true;
    }


    private class CarrosTask extends AsyncTask<Void, Void, Long> {


        @Override
        protected Long doInBackground(Void... voids) {

            try {
                return contaServiceBD.salvar(conta);

            } catch (SQLException exception) {
                return 0L;
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if (aLong > 0) {

                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_realizadacomsucesso);

            } else {
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_erroaorealizaroperacao);

            }
        }
    }
}
