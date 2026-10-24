package com.example.protege;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class banco_de_dados extends SQLiteOpenHelper {

    public banco_de_dados(Context context) {
        super(context, "empresa.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE colaboradores (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT, " +
                        "cpf TEXT, " +
                        "cargo TEXT, " +
                        "setor TEXT, " +
                        "telefone TEXT, " +
                        "email TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS colaboradores");
        onCreate(db);
    }

    public void cadastrar(String nome, String cpf, String cargo,
                          String setor, String telefone, String email) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("nome", nome);
        valores.put("cpf", cpf);
        valores.put("cargo", cargo);
        valores.put("setor", setor);
        valores.put("telefone", telefone);
        valores.put("email", email);

        db.insert("colaboradores", null, valores);

        db.close();
    }

    public Cursor listar() {

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM colaboradores",
                null
        );
    }

    public void excluir(int id) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete(
                "colaboradores",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }
}