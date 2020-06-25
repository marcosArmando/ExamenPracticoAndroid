package com.yucatancorp.examenpracticoandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

public class ConexionAInternet {

    private Context context;

    public ConexionAInternet(Context context){
        this.context = context;
    }

    public void mostrarMensaje() {

        if (!hayConexionAInternet()) {

            new AlertDialog.Builder(context)
                    .setTitle(context.getResources().getString(R.string.noHayInternet))
                    .setMessage(context.getResources().getString(R.string.reviseConexion))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

    }

    public boolean hayConexionAInternet() {
        if(context == null)  return false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }

            else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("Excepción", e.getMessage());
                }
            }
        }
        return false;
    }

}
