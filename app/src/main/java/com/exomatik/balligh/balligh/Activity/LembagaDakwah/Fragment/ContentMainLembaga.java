package com.exomatik.balligh.balligh.Activity.LembagaDakwah.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.R;
import com.xw.repo.BubbleSeekBar;

public class ContentMainLembaga extends Fragment{
    private View view;
    private UserPreference userPreference;
    private BubbleSeekBar seekBar1, seekBar2;
    private int jumlahKhutbah, jumlahTarwih;
    private TextView textKhutbah, textTarwih;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        view = paramLayoutInflater.inflate(R.layout.content_main_lembaga, paramViewGroup, false);

        seekBar1 = (BubbleSeekBar) view.findViewById(R.id.seek_bar1);
        seekBar2 = (BubbleSeekBar) view.findViewById(R.id.seek_bar2);
        textKhutbah = (TextView) view.findViewById(R.id.text_khutbah);
        textTarwih = (TextView) view.findViewById(R.id.text_ceramah);

        jumlahKhutbah = 5;
        jumlahTarwih = 10;
        textKhutbah.setText(getResources().getString(R.string.text_kutbah_jumat) + " : " + Integer.toString(jumlahKhutbah));
        textTarwih.setText(getResources().getString(R.string.text_ceramah_tarwih) + " : " + Integer.toString(jumlahTarwih));
        userPreference = new UserPreference(getActivity());
        configSeekBar();
        seekBar1.setEnabled(false);
        seekBar2.setEnabled(false);

        return view;
    }

    private void configSeekBar() {
        String totalKhutbah = getResources().getString(R.string.text_jumlah_khutbah);
        String totalTarwih = getResources().getString(R.string.text_jumlah_tarwih);
        seekBar1.getConfigBuilder()
                .min(1)
                .max(Integer.parseInt(totalKhutbah))
                .progress(jumlahKhutbah)
                .build();
        seekBar2.getConfigBuilder()
                .min(1)
                .max(Integer.parseInt(totalTarwih))
                .progress(jumlahTarwih)
                .build();
    }
}