package com.exomatik.balligh.balligh.Activity.Muballigh;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.balligh.balligh.Activity.Muballigh.Fragment.fragBioMuballigh;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelBiodataMuballigh;
import com.exomatik.balligh.balligh.Model.ModelPendidikan;
import com.exomatik.balligh.balligh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActProfilMuballigh extends AppCompatActivity {
    private ImageView btnBack, btnEdit;
    private TextView textHeader, textNama, textGelar, textAlamat, textIsiKualifikasi, textIsiKesediaan, textJumlahKutbah, textJumlahCeramah;
    private CircleImageView imgUser;
    private UserPreference userPreference;
    private View view;

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
        view = (View) findViewById(android.R.id.content);

        userPreference = new UserPreference(this);

        setData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActProfilMuballigh.this, ActMainMuballigh.class));
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
        if (userPreference.getKEY_FOTO() != null) {
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(this).load(localUri).into(imgUser);
        } else {
            imgUser.setImageResource(R.drawable.logo_balligh);
        }

        getBioMuballigh();
        getPendMuballigh();
        getKeilmuanMuballigh();
        getTablighMuballigh();
    }

    private void getKeilmuanMuballigh() {
        FirebaseDatabase.getInstance()
                .getReference(userPreference.getKEY_JENIS())
                .child(getResources().getString(R.string.text_frag_kualifikasi))
                .child(userPreference.getKEY_PHONE())
                .child(getResources().getString(R.string.text_keilmuan))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ArrayList<String> dataPT = new ArrayList<String>();
                            String ilmu = "";
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String data = snapshot.getValue(String.class);
                                dataPT.add(data);
                            }

                            int size = 0;
                            for (int a = 0; a< dataPT.size(); a++){
                                size++;
                                if (size == dataPT.size()){
                                    ilmu = ilmu + dataPT.get(a);
                                }
                                else {
                                    ilmu = ilmu + dataPT.get(a) + ", ";
                                }
                            }
                            textIsiKualifikasi.setText(ilmu);
                        } else {
                            textIsiKualifikasi.setText("Belum ada keilmuan yang dipilih");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar snackbar = Snackbar
                                .make(view, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        textIsiKualifikasi.setText("-");
                    }
                });
    }

    private void getTablighMuballigh() {
        FirebaseDatabase.getInstance()
                .getReference(userPreference.getKEY_JENIS())
                .child(getResources().getString(R.string.text_frag_kualifikasi))
                .child(userPreference.getKEY_PHONE())
                .child(getResources().getString(R.string.text_tabligh))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ArrayList<String> dataPT = new ArrayList<String>();
                            String ilmu = "";
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String data = snapshot.getValue(String.class);
                                dataPT.add(data);
                            }

                            int size = 0;
                            for (int a = 0; a< dataPT.size(); a++){
                                size++;
                                if (size == dataPT.size()){
                                    ilmu = ilmu + dataPT.get(a);
                                }
                                else {
                                    ilmu = ilmu + dataPT.get(a) + ", ";
                                }
                            }
                            textIsiKesediaan.setText(ilmu);
                        } else {
                            textIsiKesediaan.setText("Belum ada kesediaan yang dipilih");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar snackbar = Snackbar
                                .make(view, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        textIsiKesediaan.setText("-");
                    }
                });
    }

    private void getBioMuballigh() {
        Query query = FirebaseDatabase.getInstance()
                .getReference(userPreference.getKEY_JENIS())
                .child(getResources().getString(R.string.text_frag_biodata))
                .orderByChild("nomorHP")
                .equalTo(userPreference.getKEY_PHONE());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelBiodataMuballigh dataKelas = snapshot.getValue(ModelBiodataMuballigh.class);

                        textNama.setText(dataKelas.getNama());
                        fragBioMuballigh.nama = textNama.getText().toString();
                        textAlamat.setText(dataKelas.getAlamat());
                    }
                } else {
                    textNama.setText(userPreference.getKEY_NAME());
                    fragBioMuballigh.nama = userPreference.getKEY_NAME();
                    textAlamat.setText("-");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
                textAlamat.setText("-");
            }
        });
    }

    private void getPendMuballigh() {
        FirebaseDatabase.getInstance()
                .getReference(userPreference.getKEY_JENIS())
                .child(getResources().getString(R.string.text_frag_pendidikan))
                .child(userPreference.getKEY_PHONE())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ArrayList<ModelPendidikan> dataPT = new ArrayList<ModelPendidikan>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ModelPendidikan data = snapshot.getValue(ModelPendidikan.class);
                                dataPT.add(data);
                            }
                            textGelar.setText("(" + dataPT.get(0).getGelar()
                                    + dataPT.get(1).getGelar() + dataPT.get(2).getGelar() + ")");
                        } else {
                            textGelar.setText("Belum ada gelar pendidikan");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar snackbar = Snackbar
                                .make(view, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        textGelar.setText("-");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActProfilMuballigh.this, ActMainMuballigh.class));
        finish();
    }
}
