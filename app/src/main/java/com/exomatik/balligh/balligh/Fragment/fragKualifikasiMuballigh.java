package com.exomatik.balligh.balligh.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Adapter.AdapterGridKualifikasi;
import com.exomatik.balligh.balligh.Data.DataKualifikasi;
import com.exomatik.balligh.balligh.R;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragKualifikasiMuballigh extends Fragment{
    private View view;
    private GridView gridKualifikasi, gridTabligh;
    private ArrayList<String> pilihanKualifikasi = new ArrayList<String>();
    private ArrayList<String> pilihanTabligh = new ArrayList<String>();

    public fragKualifikasiMuballigh() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_kualifikasi_muballigh, container, false);

        gridKualifikasi = (GridView) view.findViewById(R.id.grid_kualifikasi);
        gridTabligh = (GridView) view.findViewById(R.id.grid_tabligh);

        setGridKualifikasi();
        setGridTabligh();

        return view;
    }

    private void setGridTabligh() {
        DataKualifikasi data = new DataKualifikasi();

        final ArrayList<String> listTabligh = data.namaTabligh();
        gridTabligh.setAdapter(new AdapterGridKualifikasi(getContext(), listTabligh));

        gridTabligh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout bgGridKualifikasi = (LinearLayout) view.findViewById(R.id.ln_grid);

                boolean cek = true;
                for (int a = 0; a < pilihanTabligh.size(); a++){
                    if (pilihanTabligh.get(a).equals(listTabligh.get(position))){
                        pilihanTabligh.remove(a);
                        cek = false;
                        bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_gray));
                    }
                }

                if (cek){
                    pilihanTabligh.add(listTabligh.get(position));
                    bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_blue_dark));
                }
            }
        });
    }

    private void setGridKualifikasi() {
        DataKualifikasi data = new DataKualifikasi();

        final ArrayList<String> listKualifikasi = data.namaKualifikasi();
        gridKualifikasi.setAdapter(new AdapterGridKualifikasi(getContext(), listKualifikasi));

        gridKualifikasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout bgGridKualifikasi = (LinearLayout) view.findViewById(R.id.ln_grid);

                boolean cek = true;
                for (int a = 0; a < pilihanKualifikasi.size(); a++){
                    if (pilihanKualifikasi.get(a).equals(listKualifikasi.get(position))){
                        pilihanKualifikasi.remove(a);
                        cek = false;
                        bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_gray));
                    }
                }

                if (cek){
                    pilihanKualifikasi.add(listKualifikasi.get(position));
                    bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_blue_dark));
                }
            }
        });
    }
}
