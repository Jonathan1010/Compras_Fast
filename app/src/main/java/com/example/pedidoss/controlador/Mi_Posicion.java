package com.example.pedidoss.controlador;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class Mi_Posicion implements LocationListener {
    public static double latitud;
    public static double longitud;
    public static boolean statusGps;
    public static Location coordenadas;
    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitud = location.getLatitude();
        longitud = location.getLongitude();
        coordenadas = location;
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        statusGps=true;


    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        statusGps=false;
    }
}
