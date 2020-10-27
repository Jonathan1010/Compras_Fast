package com.example.pedidoss.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlMapa extends SQLiteOpenHelper {
    static String nombre = "mapa";
    static int versions = 1;

    //creamos el contexto de la base de datos
    public SqlMapa(Context context) {
        super(context, nombre , null, versions);
    }


    //creamos la tabla marcador con longitud y latitud
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String markador  = "create table marcador(titulo text primary key , latitud decimal(11,9) , longitud decimal(11,9))";
        sqLiteDatabase.execSQL(markador);

    }

    //
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
