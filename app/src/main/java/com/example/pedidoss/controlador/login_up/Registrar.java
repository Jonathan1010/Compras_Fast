package com.example.pedidoss.controlador.login_up;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidoss.R;
import com.example.pedidoss.controlador.huella.Activity_Huella;
import com.example.pedidoss.controlador.mapa.mapa;
import com.example.pedidoss.modelo.bd_usuario;
import com.example.pedidoss.modelo.usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity implements View.OnClickListener{
    private EditText usuarioRegistro, passwordRegistro,nombreRegistro,apellidoRegistro;
    private Button registroPersona, cancelar,huella;
    private bd_usuario bd;
    private RadioButton radio;

    //Instanciamos el firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //variables de los datos que se va a registrar en firebase
    private String usuario = "";
    private String contraseña="";
    private String nombre="";
    private String apellido="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Instanciamos el firebase
        mAuth = FirebaseAuth.getInstance();
        //Instanciamos la base de dastos en firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        usuarioRegistro = (EditText)findViewById(R.id.usuarioRegistro);
        passwordRegistro = (EditText)findViewById(R.id.passwordRegistro);
        nombreRegistro = (EditText)findViewById(R.id.nombreRegistro);
        apellidoRegistro = (EditText) findViewById(R.id.RegistroApellidos);
        radio  = (RadioButton) findViewById(R.id.RadioButoon);
        huella=(Button)findViewById(R.id.btnh);
        huella.setOnClickListener(this);

        registroPersona=(Button) findViewById(R.id.registrarseUsuario);
        registroPersona.setOnClickListener(this);

        cancelar = (Button)findViewById(R.id.cancelar);
        cancelar.setOnClickListener(this);

        bd=new bd_usuario(this);
    }


    @Override
    public void onClick(View view) {
        if (view == registroPersona) {
            if(radio.isChecked()) {
                registroPersona.setEnabled(true);
                //cuando se hace click se guarda en la base de datos de la app
                registrarDatos();
                //cuando se hace click tambien guardamos en firebase
                registroFirebase();
            }
        }
        if(view == huella){
            Intent huellad= new Intent(Registrar.this, Activity_Huella.class);
            startActivity(huellad);
        }
        if(view == cancelar){
            Intent o= new Intent(Registrar.this, Login.class);
            startActivity(o);
        }
    }

    //En este metodo registramos los datos escritos en nuestra base de datos
    private void registrarDatos() {
        com.example.pedidoss.modelo.usuario user= new usuario();
        user.setUsuario(usuarioRegistro.getText().toString());
        user.setPassword(passwordRegistro.getText().toString());
        user.setNombre(nombreRegistro.getText().toString());
        user.setApellidos(apellidoRegistro.getText().toString());
        //validamos los datos
        if(!user.isNull()){
            //Si los campos estan vacios
            Toast.makeText(this,"Ingrese valores",Toast.LENGTH_LONG).show();
        }else if(bd.insertUsuario(user)){
            //si el registro es correcto
            Toast.makeText(this,"Registro Correcto",Toast.LENGTH_LONG).show();
            //Nos devuelve al menu para seleccionar el pedido
        }else{
            //si ya existe  un usuario
            Toast.makeText(this,"Usuario ya existente",Toast.LENGTH_LONG).show();
        }
    }

    //metodo para obtener el texto escrito en el registro
    public void registroFirebase(){
        usuario = usuarioRegistro.getText().toString();
        contraseña = passwordRegistro.getText().toString();
        nombre = nombreRegistro.getText().toString();
        apellido = apellidoRegistro.getText().toString();
        //se valida si se ingrso valores o se los dejo vacios
        if(!usuario.isEmpty() && !contraseña.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty()){
            //si el usuario ingreso 6 caracteres en su contraseña
            if(contraseña.length() >= 6){
                //si el usuario tiene 6 o mas de 6 caracteres se puede registrar en la base de firebase
                registrarUser();
            }else{
                //Para poder ingresar se necesita mas de 6 caracteres
                Toast.makeText(Registrar.this,"Ingrese mas de 6 caracteres" , Toast.LENGTH_LONG).show();
            }
        }
    }
    //metodo para registrar los datos del usuario
    public void registrarUser(){
        //creamos usuario y contraseña en nuestro firebase
        mAuth.createUserWithEmailAndPassword(usuario,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            //valida si el usuario se registro correctamente
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   //se crea un mapa para poder almacenar los datos escritos en el formulario del registro
                   Map<String , Object> map = new HashMap<>();
                   map.put("usuario", usuario);
                   map.put("contraseña", contraseña);
                   map.put("nombre", nombre);
                   map.put("apellido", apellido);
                   //se almacenan en la base de datos
                   String id = mAuth.getCurrentUser().getUid();
                   //se obtiene el id del usuario y se obtieene los valores usuario y contraseña(set value(map) nos pide un conjunto de valores
                   // que en este caso son los valores del registro)
                   mDatabase.child("users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       //Compara si se registro en firebase
                       public void onComplete(@NonNull Task<Void> task2) {
                           //si la tarea es exitosa cuando se llenan los campos cambiamos a la vista de mapa
                           if(task2.isSuccessful()){
                               Toast.makeText(Registrar.this,"Se Registro correctamente en la nube" , Toast.LENGTH_LONG).show();
                               Intent firebase= new Intent(Registrar.this, mapa.class);
                               startActivity(firebase);
                           }else{
                               //si la tarea no se pudo completar nos mostrara este mensaje:
                               Toast.makeText(Registrar.this,"No se pudo almacenar los datos correctamente" , Toast.LENGTH_LONG).show();
                           }
                       }
                   });
               }else{
                   //En el caso copntrario que no se pueda registrar
                   Toast.makeText(Registrar.this,"No se pudo registrar este usuario" , Toast.LENGTH_LONG).show();
               }
            }
        });
    }
}