package com.exomatik.balligh.balligh.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exomatik.balligh.balligh.R;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragAfiliasiMuballigh extends Fragment{
    private View view;

    public fragAfiliasiMuballigh() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_bio_muballigh, container, false);


        return view;
    }
}
