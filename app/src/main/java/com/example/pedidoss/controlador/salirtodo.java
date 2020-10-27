package com.example.pedidoss.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidoss.R;

public class salirtodo extends AppCompatActivity implements View.OnClickListener {
    private Button salir;
// se da funcion al boton salir de la ventana de compra realizada///
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);

        salir = (Button) findViewById(R.id.btnsalir);
        salir.setOnClickListener(this);

    }
    //salimos y nos vamos al menu
    @Override
    public void onClick(View v) {
        if(v==salir){
            Intent btrestaurant= new Intent(salirtodo.this, menuC.class);
            startActivity(btrestaurant);
        }
    }
}