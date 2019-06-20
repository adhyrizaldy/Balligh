package com.exomatik.balligh.balligh.Model;

/**
 * Created by IrfanRZ on 20/06/2019.
 */

public class ModelPendidikan {
    String pt, nama, kota, tahun, jurusan, gelar;

    public ModelPendidikan() {
    }

    public ModelPendidikan(String pt, String nama, String kota, String tahun, String jurusan, String gelar) {
        this.pt = pt;
        this.nama = nama;
        this.kota = kota;
        this.tahun = tahun;
        this.jurusan = jurusan;
        this.gelar = gelar;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getGelar() {
        return gelar;
    }

    public void setGelar(String gelar) {
        this.gelar = gelar;
    }
}
