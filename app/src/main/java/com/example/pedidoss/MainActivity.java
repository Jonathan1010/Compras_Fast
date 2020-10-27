package com.example.pedidoss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidoss.controlador.Login;
import com.example.pedidoss.controlador.mapa;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button selectUBI , iniciar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciar=(Button) findViewById(R.id.iniciatusesion);
        iniciar.setOnClickListener(this);
        selectUBI =(Button) findViewById(R.id.selectUBI);
        selectUBI.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        //cambiamos de activiti a la de mapa
        if (view == selectUBI) {
            Intent selectUBI = new Intent(MainActivity.this, mapa.class);
            startActivity(selectUBI);
        }
        if (view == iniciar) {
            //cambiamos de activiti a la de el login
            Intent iniciar = new Intent(MainActivity.this, Login.class);
            startActivity(iniciar);
        }
    }
}