package com.exomatik.balligh.balligh.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.balligh.balligh.R;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class AdapterGridKualifikasi extends BaseAdapter {
    private Context context;
    private ArrayList<String> dataWarna;

    public AdapterGridKualifikasi(Context context, ArrayList<String> dataWarna) {
        this.context = context;
        this.dataWarna = dataWarna;
    }

    @Override
    public int getCount() {
        return dataWarna.size();
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

        text.setText(dataWarna.get(position));

        return v;
    }
}
