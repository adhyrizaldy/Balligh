package com.exomatik.balligh.balligh.Activity.Muballigh;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exomatik.balligh.balligh.Adapter.ViewPagerAdapter;
import com.exomatik.balligh.balligh.Fragment.fragAfiliasiMuballigh;
import com.exomatik.balligh.balligh.Fragment.fragBioMuballigh;
import com.exomatik.balligh.balligh.Fragment.fragKualifikasiMuballigh;
import com.exomatik.balligh.balligh.Fragment.fragPendMuballigh;
import com.exomatik.balligh.balligh.R;

public class ActEditProfilMuballigh extends AppCompatActivity {
    private TabLayout tabKategori;
    private ViewPager viewKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_profil_muballigh);

        tabKategori = (TabLayout) findViewById(R.id.tab_menu_edit);
        viewKategori = (ViewPager) findViewById(R.id.view_menu_edit);

        ViewPagerAdapter adapterKategori = new ViewPagerAdapter(getSupportFragmentManager());

        adapterKategori.AddFragment(new fragBioMuballigh());
        adapterKategori.AddFragment(new fragPendMuballigh());
        adapterKategori.AddFragment(new fragAfiliasiMuballigh());
        adapterKategori.AddFragment(new fragKualifikasiMuballigh());

        viewKategori.setAdapter(adapterKategori);
        tabKategori.setupWithViewPager(viewKategori);

        tabKategori.getTabAt(0).setText("Biodata");
        tabKategori.getTabAt(1).setText("Pendidikan");
        tabKategori.getTabAt(2).setText("Afiliasi Lembaga");
        tabKategori.getTabAt(3).setText("Kualifikasi");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActEditProfilMuballigh.this, ActProfilMuballigh.class));
        finish();
    }
}
