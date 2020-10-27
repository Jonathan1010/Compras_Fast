package com.example.pedidoss.controlador;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pedidoss.R;


//Se requiere de una version del sdk para poder utilizar FingerprinbtManager
//Esta es una actualizacion antigua por lo cual nos marca como subrayado
@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }


    //En este metodo nos da los permisos para la huella
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    //En este metodo nos actualizara y mostrara la utenticacion con la huella si en el caso ya la tengamos guardada
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Error de Autenticación de huellas dactilares\n" + errString, false);
    }

    //En este metodo nos mostrara cuando no se pudo pulsar la huella digital
    @Override
    public void onAuthenticationFailed() {
        this.update("Fallo al autenticar con la huella dactilar.", false);
    }

    //Este metodo nos sirve para si se toco la huela digital
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Éxito al autenticar con la huella dactilar.", true);
    }

    //aqui actualizamos nuestro TextView para fijar que toco la huella dactilar
    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
        if(success){
            //Si la actulizacion es exitosa obtenemos y cambiamos el color del texto
            textView.setTextColor(ContextCompat.getColor(context,R.color.successText));
        }
    }
}
