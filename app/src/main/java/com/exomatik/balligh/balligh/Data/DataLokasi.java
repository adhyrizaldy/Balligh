package com.exomatik.balligh.balligh.Data;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 18/06/2019.
 */

public class DataLokasi {

    public DataLokasi() {
    }

    public ArrayList<String> namaProvinsi() {
        ArrayList<String> nama = new ArrayList<String>();

        nama.add("Aceh");
        nama.add("Sulawesi Selatan");
        nama.add("Sulawesi Tenggara");
        nama.add("Sulawesi Tengah");
        nama.add("Sulawesi Utara");
        nama.add("Sulawesi Barat");
        nama.add("Sulawesi Tengah");
        nama.add("Sumatera Utara");
        nama.add("Sumatera Barat");
        nama.add("Sumatera Selatan");
        nama.add("Riat");
        nama.add("Kepulauan Riau");
        nama.add("Jambi");
        nama.add("Bengkulu");
        nama.add("Kepulauan Bangka Belitung");
        nama.add("Lampung");
        nama.add("Banten");
        nama.add("Jawa Barat");
        nama.add("Jakarta");
        nama.add("Jawa Tengah");
        nama.add("Jawa Timur");
        nama.add("Yogyakarta");
        nama.add("Bali");
        nama.add("Nusa Tenggara Barat");
        nama.add("Nusa Tenggara Timur");
        nama.add("Kalimantan Barat");
        nama.add("Kalimantan Selatan");
        nama.add("Kalimantan Tengah");
        nama.add("Kalimantan Timur");
        nama.add("Kalimantan Utara");
        nama.add("Gorontalo");
        nama.add("Maluku");
        nama.add("Maluku Utara");
        nama.add("Papua");
        nama.add("Papua Barat");

        return nama;
    }

    public ArrayList<String> namaKota(String provinsi) {
        ArrayList<String> nama = new ArrayList<String>();

        switch (provinsi) {
            case "Sulawesi Selatan":
                nama.add("Kabupaten Bantaeng");
                nama.add("Kabupaten Barru");
                nama.add("Kabupaten Bone");
                nama.add("Kabupaten Bulukumba");
                nama.add("Kabupaten Enreka");
                nama.add("Kabupaten Gowa");
                nama.add("Kabupaten Jeneponto");
                nama.add("Kabupaten Kepulauan Selayar");
                nama.add("Kabupaten Luwu Timur");
                nama.add("Kabupaten Luwu Utara");
                nama.add("Kabupaten Luwu");
                nama.add("Kabupaten Maros");
                nama.add("Kabupaten Pangkajene dan Kepulauan");
                nama.add("Kabupaten Pinrang");
                nama.add("Kabupaten Sidenreng Rappang");
                nama.add("Kabupaten Sinjai");
                nama.add("Kabupaten Soppeng");
                nama.add("Kabupaten Takalar");
                nama.add("Kabupaten Tana Toraja");
                nama.add("Kabupaten Toraja Utara");
                nama.add("Kabupaten Wajo");
                nama.add("Kota Makassar");
                nama.add("Kota Palopo");
                nama.add("Kota Parepare");
                break;
            default:
                nama.add("Belum ada data");
                break;

        }
        return nama;
    }

    public ArrayList<String> namaKecamatan(String kecamatan) {
        ArrayList<String> nama = new ArrayList<String>();

        nama.add("-");

        switch (kecamatan) {
            case "Kota Makassar":

                nama.add("Kota Parepare");
                break;
            default:
                nama.add("Belum ada data");
                break;

        }
        return nama;
    }
}
