package com.exomatik.balligh.balligh.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.exomatik.balligh.balligh.Activity.Muballigh.ActProfilMuballigh;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Fragment.ContentMain;
import com.exomatik.balligh.balligh.R;
import com.google.firebase.auth.FirebaseAuth;

public class ActMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private UserPreference userPreference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);


        userPreference = new UserPreference(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (userPreference.getKEY_JENIS().equals("Muballigh")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    , new ContentMain()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil) {
            startActivity(new Intent(ActMainActivity.this, ActProfilMuballigh.class));
            finish();
        }
        else if (id == R.id.nav_sign_out){
            progressDialog = new ProgressDialog(ActMainActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.progress_title1));
            progressDialog.setTitle(getResources().getString(R.string.progress_text1));
            progressDialog.setCancelable(false);
            progressDialog.show();
            FirebaseAuth.getInstance().signOut();
            hapusUser();
            startActivity(new Intent(ActMainActivity.this, ActSplashScreen.class));
            progressDialog.dismiss();
            finish();
        }
//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hapusUser() {
        userPreference.setKEY_NAME(null);
        userPreference.setKEY_EMAIL(null);
        userPreference.setKEY_PHONE(null);
        userPreference.setKEY_FOTO(null);
        userPreference.setKEY_UID(null);
        userPreference.setKEY_JENIS(null);
    }
}
