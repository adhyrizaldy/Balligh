package com.exomatik.balligh.balligh.Model;

/**
 * Created by IrfanRZ on 13/06/2019.
 */

public class ModelUser {
    String namaLengkap, email, noHp, uid, foto, jenisAkun;

    public ModelUser() {
    }

    public ModelUser(String namaLengkap, String email, String noHp, String uid, String foto, String jenisAkun) {
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.noHp = noHp;
        this.uid = uid;
        this.foto = foto;
        this.jenisAkun = jenisAkun;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJenisAkun() {
        return jenisAkun;
    }

    public void setJenisAkun(String jenisAkun) {
        this.jenisAkun = jenisAkun;
    }
}
