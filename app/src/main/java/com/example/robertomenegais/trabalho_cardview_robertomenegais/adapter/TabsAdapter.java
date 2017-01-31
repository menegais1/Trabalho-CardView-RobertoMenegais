package com.example.robertomenegais.trabalho_cardview_robertomenegais.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.robertomenegais.trabalho_cardview_robertomenegais.R;
import com.example.robertomenegais.trabalho_cardview_robertomenegais.fragment.ContasFragment;

/**
 * Created by Roberto Menegais on 28/01/2017.
 */

public class TabsAdapter extends FragmentPagerAdapter {


    private Context context;

    public TabsAdapter(Context context , FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        Fragment f = null;

        switch(position){
            case 0:
                f = new ContasFragment();
                args.putString("tipo",context.getString(R.string.tabs_corrente));
                break;
            case 1:
                f = new ContasFragment();
                args.putString("tipo",context.getString(R.string.tabs_poupanca));
                break;
            case 2:
                f = new ContasFragment();
                args.putString("tipo",context.getString(R.string.tabs_salario));
                break;
            case 3:
                f = new ContasFragment();
                args.putString("tipo",context.getString(R.string.tabs_todos));
                break;

        }

        f.setArguments(args);
        return f ;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:{
                return context.getString(R.string.tabs_corrente);
            }
            case 1:
                return context.getString(R.string.tabs_poupanca);
            case 2:
                return context.getString(R.string.tabs_salario);
            case 3:
                return context.getString(R.string.tabs_todos);

        }
        return null;
    }
}
