package com.exomatik.balligh.balligh.Activity.PengurusMasjid.Fragment;

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

public class ContentMainMasjid extends Fragment{
    private View view;
    private UserPreference userPreference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        view = paramLayoutInflater.inflate(R.layout.content_main_masjid, paramViewGroup, false);

        userPreference = new UserPreference(getActivity());

        return view;
    }
}