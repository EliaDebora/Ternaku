package com.fintech.ternaku.model;

/**
 * Created by YORIS on 9/9/16.
 */
public class Ternak {
    private String id_ternak;
    private String nama_ternak;
    private float berat_badan;
    private float produksi_susu;
    private String status_kesuburan;
    private String diagnosis;
    private String tgl_subur;

    public Ternak() {

    }
    public Ternak(String id_ternak, String nama_ternak, float berat_badan, float produksi_susu, String status_kesuburan, String diagnosis, String tgl_subur) {
        this.setId_ternak(id_ternak);
        this.setNama_ternak(nama_ternak);
        this.setBerat_badan(berat_badan);
        this.setProduksi_susu(produksi_susu);
        this.setStatus_kesuburan(status_kesuburan);
        this.setDiagnosis(diagnosis);
        this.setTgl_subur(tgl_subur);
    }

    public String getId_ternak() {
        return id_ternak;
    }

    public void setId_ternak(String id_ternak) {
        this.id_ternak = id_ternak;
    }

    public String getNama_ternak() {
        return nama_ternak;
    }

    public void setNama_ternak(String nama_ternak) {
        this.nama_ternak = nama_ternak;
    }

    public float getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(float berat_badan) {
        this.berat_badan = berat_badan;
    }

    public float getProduksi_susu() {
        return produksi_susu;
    }

    public void setProduksi_susu(float produksi_susu) {
        this.produksi_susu = produksi_susu;
    }

    public String getStatus_kesuburan() {
        return status_kesuburan;
    }

    public void setStatus_kesuburan(String status_kesuburan) {
        this.status_kesuburan = status_kesuburan;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTgl_subur() {
        return tgl_subur;
    }

    public void setTgl_subur(String tgl_subur) {
        this.tgl_subur = tgl_subur;
    }
}
