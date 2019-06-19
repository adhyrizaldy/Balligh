package com.exomatik.balligh.balligh.Activity.Muballigh;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.balligh.balligh.Activity.ActMainActivity;
import com.exomatik.balligh.balligh.Activity.ActSplashScreen;
import com.exomatik.balligh.balligh.Activity.Authentication.ActWelcome;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelBiodataMuballigh;
import com.exomatik.balligh.balligh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActProfilMuballigh extends AppCompatActivity {
    private ImageView btnBack, btnEdit;
    private TextView textHeader, textNama, textGelar, textAlamat, textIsiKualifikasi
            , textIsiKesediaan, textJumlahKutbah, textJumlahCeramah;
    private CircleImageView imgUser;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profil_muballigh);

        btnBack = (ImageView) findViewById(R.id.img_back);
        btnEdit = (ImageView) findViewById(R.id.img_edit);
        imgUser = (CircleImageView) findViewById(R.id.img_user);
        textHeader = (TextView) findViewById(R.id.text_header);
        textNama = (TextView) findViewById(R.id.text_nama);
        textGelar = (TextView) findViewById(R.id.text_gelar);
        textAlamat = (TextView) findViewById(R.id.text_alamat);
        textIsiKualifikasi = (TextView) findViewById(R.id.text_isi_kualifikasi);
        textIsiKesediaan = (TextView) findViewById(R.id.text_isi_kesediaan);
        textJumlahKutbah = (TextView) findViewById(R.id.text_jumlah_khutbah);
        textJumlahCeramah = (TextView) findViewById(R.id.text_jumlah_ceramah);

        userPreference = new UserPreference(this);

        setData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActProfilMuballigh.this, ActMainActivity.class));
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActProfilMuballigh.this, ActEditProfilMuballigh.class));
                finish();
            }
        });
    }

    private void setData() {
        if (userPreference.getKEY_FOTO() != null){
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(this).load(localUri).into(imgUser);
        }else {
            imgUser.setImageResource(R.drawable.logo_balligh);
        }
        textNama.setText(userPreference.getKEY_NAME());
        getBioMuballigh();
    }

    private void getBioMuballigh(){
        Query query = FirebaseDatabase.getInstance()
                .getReference(userPreference.getKEY_JENIS())
                .child(getResources().getString(R.string.text_frag_biodata))
                .orderByChild("nomorHP")
                .equalTo(userPreference.getKEY_PHONE());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ModelBiodataMuballigh dataKelas = snapshot.getValue(ModelBiodataMuballigh.class);

                        textAlamat.setText(dataKelas.getAlamat());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActProfilMuballigh.this, ActMainActivity.class));
        finish();
    }
}
