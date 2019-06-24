package com.exomatik.balligh.balligh.Activity.LembagaDakwah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Activity.ActSplashScreen;
import com.exomatik.balligh.balligh.Activity.LembagaDakwah.Fragment.ContentMainLembaga;
import com.exomatik.balligh.balligh.Activity.Muballigh.ActMainMuballigh;
import com.exomatik.balligh.balligh.Activity.PengurusMasjid.ActMainMasjid;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Activity.Muballigh.Fragment.ContentMainMuballigh;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActMainLembaga extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private UserPreference userPreference;
    private ProgressDialog progressDialog;
    private CircleImageView imgUser, bgLd, bgMj, bgMb, bgMs;
    private TextView namaUser;
    private View view;
    private RelativeLayout btnLd, btnMj, btnMb, btnMs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_lembaga);

        userPreference = new UserPreference(this);
        btnLd = (RelativeLayout) findViewById(R.id.jenis1);
        btnMj = (RelativeLayout) findViewById(R.id.jenis2);
        btnMb = (RelativeLayout) findViewById(R.id.jenis3);
        btnMs = (RelativeLayout) findViewById(R.id.jenis4);
        bgLd = (CircleImageView) findViewById(R.id.img_icon_1);
        bgMj = (CircleImageView) findViewById(R.id.img_icon_2);
        bgMb = (CircleImageView) findViewById(R.id.img_icon_3);
        bgMs = (CircleImageView) findViewById(R.id.img_icon_4);
        view = (View) findViewById(android.R.id.content);

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

        View localView = navigationView.getHeaderView(0);
        imgUser = ((CircleImageView) localView.findViewById(R.id.image_person));
        namaUser = ((TextView) localView.findViewById(R.id.text_nama));

        namaUser.setText(userPreference.getKEY_NAME());
        setJenisAkunBg();
        if (userPreference.getKEY_FOTO() != null) {
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(this).load(localUri).into(imgUser);
        } else {
            imgUser.setImageResource(R.drawable.logo_balligh);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    , new ContentMainLembaga()).commit();

        btnLd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekPosisiAkun(getResources().getString(R.string.jenis_akun_1));
            }
        });

        btnMb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekPosisiAkun(getResources().getString(R.string.jenis_akun_3));
            }
        });

        btnMj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekPosisiAkun(getResources().getString(R.string.jenis_akun_2));
            }
        });

        btnMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(v, getResources().getString(R.string.error_fitur_not_ready), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
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
            startActivity(new Intent(ActMainLembaga.this, ActProfilLembaga.class));
            finish();
        }
        else if (id == R.id.nav_jadwal){
            cekProfil();
        }
        else if (id == R.id.nav_kontak){
            cekProfil();
        }
        else if (id == R.id.nav_pesan){
            cekProfil();
        }
        else if (id == R.id.nav_afiliasi){
            cekProfil();
        }
        else if (id == R.id.nav_sign_out) {
            progressDialog = new ProgressDialog(ActMainLembaga.this);
            progressDialog.setMessage(getResources().getString(R.string.progress_title1));
            progressDialog.setTitle(getResources().getString(R.string.progress_text1));
            progressDialog.setCancelable(false);
            progressDialog.show();
            FirebaseAuth.getInstance().signOut();
            hapusUser();
            startActivity(new Intent(ActMainLembaga.this, ActSplashScreen.class));
            progressDialog.dismiss();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cekProfil(){
        Query query = FirebaseDatabase.getInstance()
                .getReference(userPreference.getKEY_JENIS())
                .child(getResources().getString(R.string.text_frag_biodata))
                .orderByChild("nomorHP")
                .equalTo(userPreference.getKEY_PHONE());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Toast.makeText(ActMainLembaga.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(view, getResources().getString(R.string.toast_lengkapi_profile), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void hapusUser() {
        userPreference.setKEY_NAME(null);
        userPreference.setKEY_EMAIL(null);
        userPreference.setKEY_PHONE(null);
        userPreference.setKEY_FOTO(null);
        userPreference.setKEY_UID(null);
        userPreference.setKEY_JENIS(null);
    }

    private void cekPosisiAkun(String jenisAkun) {
        if (jenisAkun == "Lembaga Dakwah" && userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_1))) {
            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.jenis_akun_sama), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (jenisAkun == "Pengurus Masjid" && userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_2))) {
            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.jenis_akun_sama), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (jenisAkun == "Muballigh" && userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_3))) {
            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.jenis_akun_sama), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (jenisAkun == "Masyarakat" && userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_4))) {
            Snackbar snackbar = Snackbar
                    .make(view, getResources().getString(R.string.error_fitur_not_ready), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            progressDialog = new ProgressDialog(ActMainLembaga.this);
            progressDialog.setMessage(getResources().getString(R.string.progress_title1));
            progressDialog.setTitle(getResources().getString(R.string.progress_text1));
            progressDialog.setCancelable(false);
            progressDialog.show();
            sendData(jenisAkun);
        }
    }

    private void sendData(final String jenisAkun) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("users")
                .child(userPreference.getKEY_PHONE())
                .child("jenisAkun")
                .setValue(jenisAkun)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            userPreference.setKEY_JENIS(jenisAkun);
                            Toast.makeText(ActMainLembaga.this, getResources().getString(R.string.succes_swithc), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActMainLembaga.this, ActSplashScreen.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(view, getResources().getString(R.string.error) + task.getException().getMessage().toString(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ActMainLembaga.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setJenisAkunBg() {
        if (userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_1))) {
            bgLd.setImageResource(R.drawable.icon_blue);
            bgMb.setImageResource(R.drawable.background_white_rounded);
            bgMs.setImageResource(R.drawable.background_white_rounded);
            bgMj.setImageResource(R.drawable.background_white_rounded);
        } else if (userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_2))) {
            bgMj.setImageResource(R.drawable.icon_blue);
            bgLd.setImageResource(R.drawable.background_white_rounded);
            bgMs.setImageResource(R.drawable.background_white_rounded);
            bgMb.setImageResource(R.drawable.background_white_rounded);
        } else if (userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_3))) {
            bgMb.setImageResource(R.drawable.icon_blue);
            bgLd.setImageResource(R.drawable.background_white_rounded);
            bgMs.setImageResource(R.drawable.background_white_rounded);
            bgMj.setImageResource(R.drawable.background_white_rounded);
        } else if (userPreference.getKEY_JENIS().equals(getResources().getString(R.string.jenis_akun_4))) {
            bgMs.setImageResource(R.drawable.icon_blue);
            bgLd.setImageResource(R.drawable.background_white_rounded);
            bgMb.setImageResource(R.drawable.background_white_rounded);
            bgMj.setImageResource(R.drawable.background_white_rounded);
        }
    }
}
