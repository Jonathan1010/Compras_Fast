package com.example.pedidoss.controlador.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidoss.R;
import com.example.pedidoss.controlador.mapa.mapa;

public class menuC extends AppCompatActivity implements View.OnClickListener {
    ///aqui declaramos las imgbotones////

    private  ImageButton btrestaurant, farmacia, bebida, deporte, tienda, regalo, tecnologia, mascota, otro;
    private Button salir;

    //// Aqui les damos funcion a todos los imgbotones////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_c);
        btrestaurant = (ImageButton) findViewById(R.id.imagerestaurant);
        farmacia = (ImageButton) findViewById(R.id.imgfarmacia);
        bebida = (ImageButton) findViewById(R.id.imgbebida);
        deporte = (ImageButton) findViewById(R.id.imgdeporte);
        tienda = (ImageButton) findViewById(R.id.imgtienda);
        regalo = (ImageButton) findViewById(R.id.imgregalo);
        tecnologia = (ImageButton) findViewById(R.id.imgtecnologia);
        mascota = (ImageButton) findViewById(R.id.imgmascota);
        otro = (ImageButton) findViewById(R.id.imgotros);
        salir=(Button)findViewById(R.id.salirMenu);
        salir.setOnClickListener(this);



        btrestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, Restaurante.class);
                startActivity(btrestaurant);
            }
        });
        farmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, com.example.pedidoss.controlador.menu.farmacia.class);
                startActivity(btrestaurant);
            }
        });
        bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, bebidas.class);
                startActivity(btrestaurant);
            }
        });
        deporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, com.example.pedidoss.controlador.menu.deporte.class);
                startActivity(btrestaurant);
            }
        });
        tienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, com.example.pedidoss.controlador.menu.tienda.class);
                startActivity(btrestaurant);
            }
        });
        regalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, com.example.pedidoss.controlador.menu.regalo.class);
                startActivity(btrestaurant);
            }
        });
        tecnologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, com.example.pedidoss.controlador.menu.tecnologia.class);
                startActivity(btrestaurant);
            }
        });

        mascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, mascota.class);
                startActivity(btrestaurant);
            }
        });
        otro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btrestaurant = new Intent(menuC.this, otros.class);
                startActivity(btrestaurant);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==salir){
            Intent s = new Intent(this, mapa.class);
            startActivity(s);
        }
    }
}





