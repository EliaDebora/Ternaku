package com.fintech.ternaku.model;

/**
 * Created by YORIS on 9/9/16.
 */
public class Ternak {
    private String id_ternak;
    private String nama_ternak;
    private float berat_badan;
    private int hari_produksi_susu;
    private String status_kesuburan;
    private String diagnosis;
    private String tgl_subur;
    private String tgl_lahir;
    private String aktivitas;
    private float max_susu;
    private float min_susu;
    private float avg_susu;
    private float total_susu;
    private float jml_susu;

    public Ternak() {

    }
    public Ternak(String id_ternak, String nama_ternak, float berat_badan, int hari_produksi_susu, String status_kesuburan, String diagnosis, String tgl_subur, String tgl_lahir, String aktivitas, float total_susu, float jml_susu) {
        this.setId_ternak(id_ternak);
        this.setNama_ternak(nama_ternak);
        this.setBerat_badan(berat_badan);
        this.setProduksi_susu(hari_produksi_susu);
        this.setStatus_kesuburan(status_kesuburan);
        this.setDiagnosis(diagnosis);
        this.setTgl_subur(tgl_subur);
        this.setTgl_lahir(tgl_lahir);
        this.setAktivitas(aktivitas);
        this.setMax_susu(getMax_susu());
        this.setMin_susu(getMin_susu());
        this.setAvg_susu(getAvg_susu());
        this.setTotal_susu(total_susu);
        this.setJml_susu(jml_susu);
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

    public int getProduksi_susu() {
        return hari_produksi_susu;
    }

    public void setProduksi_susu(int hari_produksi_susu) {
        this.hari_produksi_susu = hari_produksi_susu;
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

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }

    public float getMax_susu() {
        return max_susu;
    }

    public void setMax_susu(float max_susu) {
        this.max_susu = max_susu;
    }

    public float getMin_susu() {
        return min_susu;
    }

    public void setMin_susu(float min_susu) {
        this.min_susu = min_susu;
    }

    public float getAvg_susu() {
        return avg_susu;
    }

    public void setAvg_susu(float avg_susu) {
        this.avg_susu = avg_susu;
    }

    public float getTotal_susu() {
        return total_susu;
    }

    public void setTotal_susu(float total_susu) {
        this.total_susu = total_susu;
    }

    public float getJml_susu() {
        return jml_susu;
    }

    public void setJml_susu(float jml_susu) {
        this.jml_susu = jml_susu;
    }
}
