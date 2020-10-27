package com.example.pedidoss.controlador.login_up;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidoss.R;
import com.example.pedidoss.controlador.mapa.mapa;
import com.example.pedidoss.modelo.bd_usuario;
import com.example.pedidoss.modelo.usuario;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText user, pass;
    private Button entrar , registrar;
    private bd_usuario bd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText)findViewById(R.id.usuario);
        pass = (EditText)findViewById(R.id.password);

        entrar = (Button) findViewById(R.id.iniciarsesion);
        registrar=(Button) findViewById(R.id.registrarse);


        entrar.setOnClickListener(this);
        registrar.setOnClickListener(this);
        bd = new bd_usuario(this);

    }

    @Override
    public void onClick(View view) {
        if (view == entrar) {
            //obtenermos el valor que se ingresa en el login
            String u = user.getText().toString();
            //obtenemos el valor que se ingresa de la contraseña del login
            String p = pass.getText().toString();
            //validamos los campos si estan vacios
            if(u.equals("") && p.equals("")){
                Toast.makeText(this, "Ingrese Datos Campos Vacios",Toast.LENGTH_LONG).show();
                //validamos los datos si se encuentran en la base de datos en el metodo login de bd_usuario
                //lo igualamos a uno ya que en el metodo login esta fijado el valor a cero y si es 0 es que no encuentra el usuario en la base de datos
                //si el valor a en Login es igual a 1 es que si lo encontro en la base de datos
            }else if(bd.login(u ,p) == 1){
                //Si los datos estan en la base de datos, se da acceso
                usuario us = bd.getUsuario(u,p);
                Toast.makeText(this, "Datos Correctos",Toast.LENGTH_LONG).show();
                Intent r2 = new Intent(Login.this, mapa.class);
                startActivity(r2);
                finish();
            }else{
                //validamos si no se escribe ni usuario ni contraseña
                Toast.makeText(this, "Datos Incorrectos ",Toast.LENGTH_LONG).show();
            }

        }
        if (view == registrar) {
            //Cambiamos de activity a la de registrar
            Intent registro = new Intent(Login.this, Registrar.class);
            startActivity(registro);
        }
    }
}