package com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.robertomenegais.trabalho_cardview_robertomenegais.R;

/**
 * Created by Roberto Menegais on 28/01/2017.
 */

public class BaseFragment extends Fragment {
    protected static final String TAG = "RobertoMenegais";
    private AlertDialog dialog;


    protected void replaceFragment(int container, Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();

    }

    protected void alertOk(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //cria um buider
        builder.setTitle(title).setMessage(message); //insere o título e a mensagem
        // Adiciona o botão e trata o evento onClick
        builder.setPositiveButton(R.string.alertdialog_button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //volta para a lista de carros
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create(); //cria o alerta
        dialog.show(); //apresenta a caixa de diálogo
    }

    protected void alertOk(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //cria um buider
        builder.setTitle(title).setMessage(message); //insere o título e a mensagem
        // Adiciona o botão e trata o evento onClick
        builder.setPositiveButton(R.string.alertdialog_button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //volta para a lista de carros
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create(); //cria o alerta
        dialog.show(); //apresenta a caixa de diálogo
    }

    protected void alertWait(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //cria um buider
        builder.setTitle(title).setMessage(message); //insere o título e a mensagem
        dialog = builder.create(); //cria o alerta
        dialog.show(); //apresenta a caixa de diálogo
    }

    protected void alertWaitDismiss(){
        if(dialog != null){
            dialog.dismiss();
        }
    }


}
