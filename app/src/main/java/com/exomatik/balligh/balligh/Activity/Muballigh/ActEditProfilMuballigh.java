package com.exomatik.balligh.balligh.Activity.Muballigh;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.exomatik.balligh.balligh.Adapter.ViewPagerAdapter;
import com.exomatik.balligh.balligh.Activity.Muballigh.Fragment.fragAfiliasiMuballigh;
import com.exomatik.balligh.balligh.Activity.Muballigh.Fragment.fragBioMuballigh;
import com.exomatik.balligh.balligh.Activity.Muballigh.Fragment.fragKualifikasiMuballigh;
import com.exomatik.balligh.balligh.Activity.Muballigh.Fragment.fragPendMuballigh;
import com.exomatik.balligh.balligh.R;

public class ActEditProfilMuballigh extends AppCompatActivity {
    private TabLayout tabKategori;
    private ViewPager viewKategori;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_profil_muballigh);

        btnBack = (ImageView) findViewById(R.id.img_back);
        tabKategori = (TabLayout) findViewById(R.id.tab_menu_edit);
        viewKategori = (ViewPager) findViewById(R.id.view_menu_edit);

        ViewPagerAdapter adapterKategori = new ViewPagerAdapter(getSupportFragmentManager());

        adapterKategori.AddFragment(new fragBioMuballigh());
        adapterKategori.AddFragment(new fragPendMuballigh());
        adapterKategori.AddFragment(new fragAfiliasiMuballigh());
        adapterKategori.AddFragment(new fragKualifikasiMuballigh());

        viewKategori.setAdapter(adapterKategori);
        tabKategori.setupWithViewPager(viewKategori);

        tabKategori.getTabAt(0).setText(getResources().getString(R.string.text_frag_biodata));
        tabKategori.getTabAt(1).setText(getResources().getString(R.string.text_frag_pendidikan));
        tabKategori.getTabAt(2).setText(getResources().getString(R.string.text_frag_afiliasi));
        tabKategori.getTabAt(3).setText(getResources().getString(R.string.text_frag_kualifikasi));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragBioMuballigh.nama = "";
                startActivity(new Intent(ActEditProfilMuballigh.this, ActProfilMuballigh.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        fragBioMuballigh.nama = "";
        startActivity(new Intent(ActEditProfilMuballigh.this, ActProfilMuballigh.class));
        finish();
    }
}
