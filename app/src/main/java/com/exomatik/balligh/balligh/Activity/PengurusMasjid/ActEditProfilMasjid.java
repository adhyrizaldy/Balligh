package com.exomatik.balligh.balligh.Activity.PengurusMasjid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.exomatik.balligh.balligh.Activity.LembagaDakwah.ActProfilLembaga;
import com.exomatik.balligh.balligh.Activity.LembagaDakwah.Fragment.fragProfilLembaga;
import com.exomatik.balligh.balligh.Activity.Muballigh.Fragment.fragBioMuballigh;
import com.exomatik.balligh.balligh.Activity.PengurusMasjid.Fragment.fragProfilMasjid;
import com.exomatik.balligh.balligh.Adapter.ViewPagerAdapter;
import com.exomatik.balligh.balligh.R;

public class ActEditProfilMasjid extends AppCompatActivity {
    private TabLayout tabKategori;
    private ViewPager viewKategori;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_profil_masjid);

        btnBack = (ImageView) findViewById(R.id.img_back);
        tabKategori = (TabLayout) findViewById(R.id.tab_menu_edit);
        viewKategori = (ViewPager) findViewById(R.id.view_menu_edit);

        ViewPagerAdapter adapterKategori = new ViewPagerAdapter(getSupportFragmentManager());

        adapterKategori.AddFragment(new fragProfilMasjid());
        adapterKategori.AddFragment(new fragProfilMasjid());

        viewKategori.setAdapter(adapterKategori);
        tabKategori.setupWithViewPager(viewKategori);

        tabKategori.getTabAt(0).setText(getResources().getString(R.string.text_profil_pengurus));
        tabKategori.getTabAt(1).setText(getResources().getString(R.string.text_frag_afiliasi));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragBioMuballigh.nama = "";
                startActivity(new Intent(ActEditProfilMasjid.this, ActProfilMasjid.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        fragBioMuballigh.nama = "";
        startActivity(new Intent(ActEditProfilMasjid.this, ActProfilMasjid.class));
        finish();
    }
}
