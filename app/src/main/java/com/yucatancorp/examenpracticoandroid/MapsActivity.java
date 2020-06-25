package com.yucatancorp.examenpracticoandroid;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import static com.yucatancorp.examenpracticoandroid.DecargarInformacion.informacionObtenida;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final String URLBase = "http://157.245.227.216:3000/api/obtieneCoordenadasXUsuario/";
    private GoogleMap mMap;
    private respuestaPOJO respuestaPOJO1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ConexionAInternet conexionAInternet = new ConexionAInternet(this);
        conexionAInternet.mostrarMensaje();

        descargarInformacion descargarInformacion = new descargarInformacion(respuestaPOJO1);
        descargarInformacion.execute(URLBase);
        respuestaPOJO1 = descargarInformacion.respuesta;

    }



    public class descargarInformacion extends AsyncTask<String, respuestaPOJO, Void> {

        respuestaPOJO respuesta = null;

        descargarInformacion(respuestaPOJO respuestaPOJO){
            respuestaPOJO = respuesta;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                respuesta = informacionObtenida(strings[0]);
            } catch (IOException | JSONException | ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LatLng ultimaUbicacion = new LatLng(respuesta.getLatitud(), respuesta.getLongitud());
            mMap.addMarker(new MarkerOptions().position(ultimaUbicacion).title(getResources().getString(R.string.ultimaUbicacion)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ultimaUbicacion));
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.seEncontro), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
