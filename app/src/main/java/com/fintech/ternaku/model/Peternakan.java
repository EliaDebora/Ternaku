package com.fintech.ternaku.model;

/**
 * Created by YORIS on 9/12/16.
 */
public class Peternakan {

    private String id_peternakan;
    private String id_pengguna;
    private String nama_peternakan;
    private String alamat;
    private String telpon;
    private String latitude;
    private String longitude;
    private String jenis_ternak;

    public Peternakan() {

    }

    public Peternakan(String id_peternakan, String id_pengguna, String nama_peternakan, String alamat, String telpon, String latitude, String longitude, String jenis_ternak ) {
        this.setId_peternakan(id_peternakan);
        this.setId_pengguna(id_pengguna);
        this.setNama_peternakan(nama_peternakan);
        this.setAlamat(alamat);
        this.setTelpon(telpon);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setJenis_ternak(jenis_ternak);
    }

    public String getId_peternakan() {
        return id_peternakan;
    }

    public void setId_peternakan(String id_peternakan) {
        this.id_peternakan = id_peternakan;
    }

    public String getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(String id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public String getNama_peternakan() {
        return nama_peternakan;
    }

    public void setNama_peternakan(String nama_peternakan) {
        this.nama_peternakan = nama_peternakan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getJenis_ternak() {
        return jenis_ternak;
    }

    public void setJenis_ternak(String jenis_ternak) {
        this.jenis_ternak = jenis_ternak;
    }
}
