package com.exomatik.balligh.balligh.Model;

/**
 * Created by IrfanRZ on 18/06/2019.
 */

public class ModelBiodataMuballigh {
    String nama, nomorHP, jk, tempatLahir, tanggalLahir, provinsi, kota, alamat, foto, locationPoint;


    public ModelBiodataMuballigh() {
    }

    public ModelBiodataMuballigh(String nama, String nomorHP, String jk, String tempatLahir, String tanggalLahir
            , String provinsi, String kota, String alamat, String foto, String locationPoint) {
        this.nama = nama;
        this.nomorHP = nomorHP;
        this.jk = jk;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.provinsi = provinsi;
        this.kota = kota;
        this.alamat = alamat;
        this.foto = foto;
        this.locationPoint = locationPoint;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
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

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLocationPoint() {
        return locationPoint;
    }

    public void setLocationPoint(String locationPoint) {
        this.locationPoint = locationPoint;
    }
}
