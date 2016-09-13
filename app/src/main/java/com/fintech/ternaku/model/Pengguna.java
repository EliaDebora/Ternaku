package com.fintech.ternaku.model;

/**
 * Created by YORIS on 9/6/16.
 */
public class Pengguna {
    private String _idrole;
    private String _idpengguna;
    private String _nama;
    private String _alamat;
    private String _telpon;
    private String _username;

    public Pengguna(){

    }

    // constructor
    public Pengguna(String idrole, String idpengguna, String nama, String alamat, String telpon, String username){
        this.set_idrole(idrole);
        this.set_idpengguna(idpengguna);
        this.set_nama(nama);
        this.set_alamat(alamat);
        this.set_telpon(telpon);
        this.set_username(username);
    }

    public String get_idrole() {
        return _idrole;
    }

    public void set_idrole(String _idrole) {
        this._idrole = _idrole;
    }

    public String get_idpengguna() {
        return _idpengguna;
    }

    public void set_idpengguna(String _idpengguna) {
        this._idpengguna = _idpengguna;
    }

    public String get_nama() {
        return _nama;
    }

    public void set_nama(String _nama) {
        this._nama = _nama;
    }

    public String get_alamat() {
        return _alamat;
    }

    public void set_alamat(String _alamat) {
        this._alamat = _alamat;
    }

    public String get_telpon() {
        return _telpon;
    }

    public void set_telpon(String _telpon) {
        this._telpon = _telpon;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }
}
