package com.exomatik.balligh.balligh.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exomatik.balligh.balligh.R;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class AdapterGridKualifikasi extends BaseAdapter {
    private Context context;
    private ArrayList<String> data;
    private ArrayList<String> tersimpan;

    public AdapterGridKualifikasi(Context context, ArrayList<String> data, ArrayList<String> tersimpan) {
        this.context = context;
        this.data = data;
        this.tersimpan = tersimpan;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.grid_kualifikasi, null);

        TextView text = (TextView) v.findViewById(R.id.text_warna);
        LinearLayout lnBackground = (LinearLayout) v.findViewById(R.id.ln_grid);

        boolean cek_kualifikasi = false;

        if (tersimpan != null){
            for (int a = 0; a < tersimpan.size(); a++){
                if (data.get(position).equals(tersimpan.get(a))){
                    cek_kualifikasi = true;
                }
            }


            if (cek_kualifikasi){
                lnBackground.setBackground(v.getResources().getDrawable(R.drawable.border_blue_dark));
            }
            else {
                lnBackground.setBackground(v.getResources().getDrawable(R.drawable.border_gray));
            }
        }

        text.setText(data.get(position));

        return v;
    }
}
