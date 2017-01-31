package com.example.robertomenegais.trabalho_cardview_robertomenegais.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Roberto Menegais on 28/01/2017.
 */

public class BaseActivity extends AppCompatActivity {

        protected static final String TAG = "RobertoMenegais";


        protected void replaceFragment(int container, Fragment fragment){
            getSupportFragmentManager().beginTransaction().replace(container,fragment).commit();
        }

}
