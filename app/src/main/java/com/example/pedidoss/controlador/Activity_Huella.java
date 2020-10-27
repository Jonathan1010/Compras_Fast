package com.example.pedidoss.controlador;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;;
import android.security.keystore.KeyProperties;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pedidoss.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


public class Activity_Huella extends AppCompatActivity implements View.OnClickListener {


    private KeyStore keyStore;
    //Variable utilizada para almacenar la llave en el contenedor de Android Keystore
    private static final String KEY_NAME = "Huella";
    private Cipher cipher;
    private TextView textView;
    private Button btnhuella;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huella);


        //Iniciando tanto el Administrador de Guarda Llaves de Android como el Administrador de Huellas Dactilares
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


        textView = (TextView) findViewById(R.id.errorText);
        btnhuella = (Button) findViewById(R.id.btnaceptarhuella);
        btnhuella.setOnClickListener(this);


        //Comprueba si el dispositivo tiene un sensor de huellas dactilares.
        if(!fingerprintManager.isHardwareDetected()){
            /**
             * Se mostrará un mensaje de error si el dispositivo no contiene el hardware de huellas dactilares.
             * Sin embargo, si planea implementar un método de autenticación por defecto,
             * puedes redirigir al usuario a una actividad de autenticación por defecto desde aquí.
             * Ejemplo:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            textView.setText("Su dispositivo no tiene un sensor de huellas dactilares");
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            //Aqui generamos nuestro id o clave primaria
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            //Esta eceocion nos ayuda a buscar errores en el supuesto caso que los haya
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            //genersamos una llave o id para nuestra huella con try catch para ni tener problemas cuanco se valla a registrar la huella
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            //si no se pudo generar un id o llave para la huella nos mostrara el siguiente menaje
            throw new RuntimeException("No se consiguió la instancia del generador de claves", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnhuella){
            Intent op= new Intent(this, Registrar.class);
            startActivity(op);
        }
    }
}