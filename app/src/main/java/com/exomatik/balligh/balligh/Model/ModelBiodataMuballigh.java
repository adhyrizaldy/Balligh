package com.exomatik.balligh.balligh.Model;

/**
 * Created by IrfanRZ on 13/06/2019.
 */

public class ModelBiodataMuballigh {
    String namaLengkap, noHp, email, jenisKelamin, tempatLahir, tanggalLahir, foto;
    boolean user;

    public ModelBiodataMuballigh() {
    }

    public ModelBiodataMuballigh(String namaLengkap, String noHp, String email, String jenisKelamin, String tempatLahir, String tanggalLahir, String foto, boolean user) {
        this.namaLengkap = namaLengkap;
        this.noHp = noHp;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.foto = foto;
        this.user = user;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }
}
