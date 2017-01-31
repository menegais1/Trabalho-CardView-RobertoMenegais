package com.example.robertomenegais.trabalho_cardview_robertomenegais.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.robertomenegais.trabalho_cardview_robertomenegais.model.Conta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto Menegais on 30/01/2017.
 */

public class ContaServiceBD extends SQLiteOpenHelper {

    private static final String TAG = "RobertoMenegais";
    private static final String NAME = "conta.sqlite";
    private static final int VERSION = 1;
    private static ContaServiceBD instance = null;

    private ContaServiceBD(Context context) {
        super(context, NAME, null, VERSION);
        getWritableDatabase();
    }

    public static ContaServiceBD getInstance(Context context) {
        if (instance == null) {
            instance = new ContaServiceBD(context);
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(TAG, "Criando banco de dados, aguarde..");

        String sql = "create table if not exists conta " +
                "(_id integer primary key autoincrement," +
                "nome text," +
                "tipo text," +
                "sobrenome text," +
                "cpf text," +
                "url_foto text," +
                "saldo numeric," +
                "limite numeric" +
                ");";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.d(TAG, "Banco de dados Criado com sucesso");
            //Populando base de dados atraves de uma thread
            new Task().execute();

            Log.d(TAG, "Tabela conta populada ");
        } catch (SQLException exception) {
            Log.d(TAG, "Erro ao criar o Banco");
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "Criando banco de dados, aguarde..");

    }

    private List<Conta> toList(Cursor cursor) {

        List<Conta> contas = new ArrayList();

        if (cursor.moveToFirst()) {
            do {
                Conta conta = new Conta();
                conta.id = cursor.getLong(cursor.getColumnIndex("_id"));
                conta.nome = cursor.getString(cursor.getColumnIndex("nome"));
                conta.sobrenome = cursor.getString(cursor.getColumnIndex("sobrenome"));
                conta.cpf = cursor.getString(cursor.getColumnIndex("cpf"));
                conta.limite = cursor.getDouble(cursor.getColumnIndex("limite"));
                conta.saldo = cursor.getDouble(cursor.getColumnIndex("saldo"));
                conta.urlFoto = cursor.getString(cursor.getColumnIndex("url_foto"));
                conta.tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                contas.add(conta);
            } while (cursor.moveToNext());
        }

        return contas;
    }


    public List<Conta> getAll() {


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();


        try {
            return toList(sqLiteDatabase.rawQuery("select * from conta", null));

        } catch (SQLiteException exception) {
            Log.d(TAG, "Erro executar instrução");
        } finally {
            sqLiteDatabase.close();
        }

        return null;
    }

    public List<Conta> getByTipo(String tipo) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();


        try {
            return toList(sqLiteDatabase.rawQuery("select * from conta where tipo ='" + tipo + "'", null));


        } catch (SQLiteException exception) {
            Log.d(TAG, "Erro executar instrução");
        } finally {
            sqLiteDatabase.close();
        }

        return null;
    }

    public long salvar(Conta conta) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();


        try {
            ContentValues values = new ContentValues();
            values.put("nome", conta.nome);
            values.put("sobrenome", conta.sobrenome);
            values.put("saldo", conta.saldo);
            values.put("limite", conta.limite);
            values.put("cpf", conta.cpf);
            values.put("tipo", conta.tipo);
            values.put("url_foto", conta.urlFoto);

            if (conta.id == null) {
                return sqLiteDatabase.insert("conta", null, values);

            } else {
                values.put("_id", conta.id);
                return sqLiteDatabase.update("conta", values, "_id=" + conta.id, null);
            }

        } catch (SQLiteException exception) {
            Log.d(TAG, "Erro executar instrução");
        } finally {
            sqLiteDatabase.close();
        }

        return 0;
    }


    public long excluir(Conta conta) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();


        try {

            return sqLiteDatabase.delete("conta", "_id=" + conta.id, null);
        } catch (SQLiteException exception) {
            Log.d(TAG, "Erro executar instrução");
        } finally {
            sqLiteDatabase.close();
        }
        return 0;


    }


    private class Task extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... voids) {
            return popularConta();
        }

        private boolean popularConta() {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();


            try {
                ContentValues values = new ContentValues();
                values.put("nome", "Roberto");
                values.put("tipo", "Corrente");
                values.put("sobrenome", "Menegais");
                values.put("saldo", 2000);
                values.put("limite", 5000);
                values.put("cpf", "11111111111");
                Log.d(TAG, values.getAsString("tipo"));
                sqLiteDatabase.insert("conta", null, values);
                Log.d(TAG, "TIPO = " + getAll().get(0));
            } catch (SQLiteException exception) {
                Log.d(TAG, "Erro ao popular a tabela");
                return false;
            } finally {
                sqLiteDatabase.close();
            }


            return true;
        }
    }
}
