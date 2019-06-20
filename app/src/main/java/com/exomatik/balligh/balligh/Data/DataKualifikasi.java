package com.exomatik.balligh.balligh.Data;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 20/06/2019.
 */

public class DataKualifikasi {

    public DataKualifikasi() {
    }

    public ArrayList<String> namaKualifikasi(){
        ArrayList<String> listKualifikasi = new ArrayList<String>();

        listKualifikasi.add("Tafsir");
        listKualifikasi.add("Ilmu Politik");
        listKualifikasi.add("Tarikh Islam");
        listKualifikasi.add("Hadits");
        listKualifikasi.add("Tib'un Nabawi");
        listKualifikasi.add("Faraidh");
        listKualifikasi.add("Hukum Islam");
        listKualifikasi.add("Tarbiyah");
        listKualifikasi.add("Kiamat");
        listKualifikasi.add("Ushuluddin");
        listKualifikasi.add("Fiqhi");
        listKualifikasi.add("Bahasa Arab");
        listKualifikasi.add("Ekonomi Syariah");
        listKualifikasi.add("Rukyah Syar'i");
        listKualifikasi.add("Sedekah/Infaq");
        listKualifikasi.add("Sirah Nabawi");

        return  listKualifikasi;
    }

    public ArrayList<String> namaTabligh(){
        ArrayList<String> listTabligh = new ArrayList<String>();

        listTabligh.add("Khutbah Jumat");
        listTabligh.add("Khutbah Ied");
        listTabligh.add("Kajian Kitab");
        listTabligh.add("Tabligh Akbar");
        listTabligh.add("Pengajian");
        listTabligh.add("Takziah");
        listTabligh.add("Nasehat Perkawinan");
        listTabligh.add("Manasik");

        return  listTabligh;
    }
}
