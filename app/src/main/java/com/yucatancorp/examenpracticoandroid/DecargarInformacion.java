package com.yucatancorp.examenpracticoandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DecargarInformacion {

    public static respuestaPOJO informacionObtenida (String urlBase) throws IOException, JSONException, ParseException {

        respuestaPOJO respuesta = null;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "usuarioid=703021");
        Request request = new Request.Builder()
                .url(urlBase)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        String data = response.body().string();

        JSONArray jsonArray = new JSONArray(data);

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String string1 = "1986-10-31T12:08:56.234-0700";
        Date resultadoMin = df1.parse(string1);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);

            String fechaEnString = object.getString("fecha");
            Date dateInObject = df1.parse(fechaEnString);

            if (dateInObject.after(resultadoMin)){

                respuesta = new respuestaPOJO(
                        object.getInt("id"),
                        object.getInt("usuarioid"),
                        object.getDouble("latitud"),
                        object.getDouble("longitud"),
                        object.getString("fecha"),
                        object.getString("createdAt"),
                        object.getString("updatedAt"));
            } else {


                Log.i("Ocurrió un Error", "no hay fecha válida");
            }
        }

        return respuesta;
    }
}