package com.example.robertomenegais.trabalho_cardview_robertomenegais.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.robertomenegais.trabalho_cardview_robertomenegais.R;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment.ContaDetalheFragment;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment.ContaNovoFragment;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.model.Conta;

/**
 * Created by Roberto Menegais on 28/01/2017.
 */

public class ContaActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CHEGUEI2");
        setContentView(R.layout.activity_conta);

        String msg = (String) getIntent().getCharSequenceExtra("qualFragmentAbrir");

        if (msg.equals("ContaNovoFragment")) {
            replaceFragment(R.id.fragment_container, new ContaNovoFragment());
        }else if(msg.equals("ContaDetalheFragment")){
            ContaDetalheFragment contaDetalheFragment = new ContaDetalheFragment();

            replaceFragment(R.id.fragment_container,contaDetalheFragment);
            Conta conta = (Conta) getIntent().getExtras().get("Conta");
            contaDetalheFragment.setConta(conta);
            Log.d(TAG,conta.toString());
        }
    }
}
