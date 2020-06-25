package com.yucatancorp.examenpracticoandroid;

public class respuestaPOJO {

    private int id;
    private int usuarioid;
    private double latitud;
    private double longitud;
    private String fecha;
    private String createdAt;
    private String updatedAt;

    public respuestaPOJO(int id, int usuarioid, double latitud, double longitud, String fecha, String createdAt, String updatedAt) {
        this.id = id;
        this.usuarioid = usuarioid;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
