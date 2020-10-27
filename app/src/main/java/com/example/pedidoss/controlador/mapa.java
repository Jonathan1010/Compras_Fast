package com.example.pedidoss.controlador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pedidoss.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class mapa extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapLongClickListener {
    private Button bguardar;
    private ImageButton bubica;
    private TextView ubicacion;
    private GoogleMap mMap;
    private Marker marcadores;
    SharedPreferences prefs;
    private LocationManager ubicacionn;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableMyLocation();
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // se declara los los botones y se les da la funcion
        bguardar = (Button) findViewById(R.id.guardar);
        ubicacion = (TextView) findViewById(R.id.ubicacion);
        bubica = findViewById(R.id.ubica);
        bubica.findViewById(R.id.ubica);
        bguardar.setOnClickListener(this);
        ubicacion.setOnClickListener(this);
    }
    // Este metodo se utiliza para cuando este listo el mapa se cargue
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
    // Este metodo lo utilizamos para saber la posicion de las cordenadas de la ubicacion
    private void miPosicion() {
        // en este if hacemos que nos pida permiso la aplicaion pra poder utlizar el gps
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        ubicacionn = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacionn.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Habilita y levanta el objeto
        LocationManager objLocalizacion = null;
        //Escucha al objeto
        LocationListener objListener;

        //en esta parte hacemos la llamada a la ubicacion tanto las longitudes como las latitudes
        objLocalizacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objListener = new Mi_Posicion();
        objLocalizacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1500, 0, objListener);
        objLocalizacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1500, 0, new milocalizacionListener());
        //este es el metodo que va hacer q nuestro indicador nos lleve a la posicion actual y tambien nos ayuda a ver si el gps esta activo o no
        if (objLocalizacion.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS Habilitado", Toast.LENGTH_LONG).show();
            LatLng loja1 = new LatLng(Mi_Posicion.latitud, Mi_Posicion.longitud);
            mMap.addMarker(new MarkerOptions().position(loja1).title("Trabajo"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loja1));
            CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(18);
            mMap.animateCamera(ZoomCam);
        } else {
            //Aqui nos mostrara si tenemos activo o no el gps en nuestro celular
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("GPS No Esta Activo");
            alert.setPositiveButton("OK", null);
            alert.create().show();
        }

    }
    //Este metodo lo uso para preguntar el permiso de localizacion a lo que se crea el activity
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
    }

    // este metodo sirve para poder marcar un marcador en el mapa
    @Override
    public void onMapLongClick(LatLng punto) {
        Toast.makeText(this, "Click posicion" + punto.latitude + punto.longitude, Toast.LENGTH_LONG).show();
        prefs = getSharedPreferences("MyPreferencia", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        Toast.makeText(mapa.this, "Click posicion" + punto.longitude + punto.latitude, Toast.LENGTH_LONG).show();
        ed.putFloat("latitud", (float) punto.latitude);
        ed.putFloat("longitud", (float) punto.longitude);
        ed.commit();
        mMap.addMarker(new MarkerOptions().position((new LatLng(punto.latitude, punto.longitude))));

    }


    // este es el metodo para darle funcion a los botonoes
    @Override
    public void onClick(View view) {
        if (view == bubica) {
            miPosicion();
        }
        if(view == bguardar){
            Toast.makeText(this,"Datos Guardados",Toast.LENGTH_LONG).show();
            Intent selectUBI = new Intent(mapa.this, menuC.class);
            startActivity(selectUBI);

        }
    }
    //en este metodo lo q hacemos es convertit las coorenadas en direcciones
    private class milocalizacionListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
            try {
                List<Address>  direccion1 = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                ubicacion.setText(direccion1.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}