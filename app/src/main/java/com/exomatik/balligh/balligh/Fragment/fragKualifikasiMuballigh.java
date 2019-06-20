package com.exomatik.balligh.balligh.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.exomatik.balligh.balligh.Activity.Muballigh.ActProfilMuballigh;
import com.exomatik.balligh.balligh.Adapter.AdapterGridKualifikasi;
import com.exomatik.balligh.balligh.Data.DataKualifikasi;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragKualifikasiMuballigh extends Fragment{
    private View view, v;
    private GridView gridKualifikasi, gridTabligh;
    private ArrayList<String> pilihanKualifikasi = new ArrayList<String>();
    private ArrayList<String> pilihanTabligh = new ArrayList<String>();
    private RelativeLayout btnUpdate;
    private ProgressDialog progressDialog;
    private UserPreference userPreference;

    public fragKualifikasiMuballigh() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_kualifikasi_muballigh, container, false);

        gridKualifikasi = (GridView) view.findViewById(R.id.grid_kualifikasi);
        gridTabligh = (GridView) view.findViewById(R.id.grid_tabligh);
        btnUpdate = (RelativeLayout) view.findViewById(R.id.rl_update);
        v = (View) view.findViewById(android.R.id.content);

        userPreference = new UserPreference(getActivity());

        getKeilmuanMuballigh();
        getTablighMuballigh();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                progressDialog.setTitle(getResources().getString(R.string.progress_text1));
                progressDialog.setCancelable(false);
                progressDialog.show();
                hapusDataKualifikasi(userPreference.getKEY_PHONE(), pilihanKualifikasi, pilihanTabligh);
            }
        });

        return view;
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
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String data = snapshot.getValue(String.class);
                                dataPT.add(data);
                            }
                            setGridKualifikasi(dataPT);
                        } else {
                            setGridKualifikasi(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        setGridKualifikasi(null);
                        Snackbar snackbar = Snackbar
                                .make(v, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
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
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String data = snapshot.getValue(String.class);
                                dataPT.add(data);
                            }
                            setGridTabligh(dataPT);
                        } else {
                            setGridTabligh(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        setGridTabligh(null);
                        Snackbar snackbar = Snackbar
                                .make(v, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
    }

    private void setGridTabligh(ArrayList<String> ilmu) {
        DataKualifikasi data = new DataKualifikasi();

        final ArrayList<String> listTabligh = data.namaTabligh();
        gridTabligh.setAdapter(new AdapterGridKualifikasi(getContext(), listTabligh, ilmu));

        pilihanTabligh = ilmu;

        gridTabligh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout bgGridKualifikasi = (LinearLayout) view.findViewById(R.id.ln_grid);

                boolean cek = true;
                for (int a = 0; a < pilihanTabligh.size(); a++){
                    if (pilihanTabligh.get(a).equals(listTabligh.get(position))){
                        pilihanTabligh.remove(a);
                        cek = false;
                        bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_gray));
                    }
                }

                if (cek){
                    pilihanTabligh.add(listTabligh.get(position));
                    bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_blue_dark));
                }
            }
        });
    }

    private void setGridKualifikasi(ArrayList<String> kual) {
        DataKualifikasi data = new DataKualifikasi();

        final ArrayList<String> listKualifikasi = data.namaKualifikasi();
        gridKualifikasi.setAdapter(new AdapterGridKualifikasi(getContext(), listKualifikasi, kual));

        pilihanKualifikasi = kual;

        gridKualifikasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout bgGridKualifikasi = (LinearLayout) view.findViewById(R.id.ln_grid);

                boolean cek = true;
                for (int a = 0; a < pilihanKualifikasi.size(); a++){
                    if (pilihanKualifikasi.get(a).equals(listKualifikasi.get(position))){
                        pilihanKualifikasi.remove(a);
                        cek = false;
                        bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_gray));
                    }
                }

                if (cek){
                    pilihanKualifikasi.add(listKualifikasi.get(position));
                    bgGridKualifikasi.setBackground(getResources().getDrawable(R.drawable.border_blue_dark));
                }
            }
        });
    }

    private void hapusDataKualifikasi(final String phone, final ArrayList<String> kualifikasi, final ArrayList<String> tabligh) {
        DatabaseReference dv_remove = FirebaseDatabase.getInstance()
                .getReference()
                .child(getResources().getString(R.string.jenis_akun_3))
                .child(getResources().getString(R.string.text_frag_kualifikasi))
                .child(phone);
        dv_remove.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                setDataKeilmuan(phone, kualifikasi, tabligh);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(v, getResources().getString(R.string.error) + e.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void setDataKeilmuan(final String phone, ArrayList<String> kualifikasi, final ArrayList<String> tabligh) {
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference
                .child(getResources().getString(R.string.jenis_akun_3))
                .child(getResources().getString(R.string.text_frag_kualifikasi))
                .child(phone)
                .child(getResources().getString(R.string.text_keilmuan))
                .setValue(kualifikasi)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> paramAnonymous2Task) {
                        if (paramAnonymous2Task.isSuccessful()) {
                            setDataTabligh(phone, tabligh);
                        } else {
                            progressDialog.dismiss();
                            Snackbar.make(view, "Gagal Upload Data, error tidak diketahui", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(view, getResources().getString(R.string.error) + e.getMessage().toString(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setDataTabligh(String phone, ArrayList<String> tabligh) {
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference
                .child(getResources().getString(R.string.jenis_akun_3))
                .child(getResources().getString(R.string.text_frag_kualifikasi))
                .child(phone)
                .child(getResources().getString(R.string.text_tabligh))
                .setValue(tabligh)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> paramAnonymous2Task) {
                        if (paramAnonymous2Task.isSuccessful()) {
                            startActivity(new Intent(getActivity(), ActProfilMuballigh.class));
                            getActivity().finish();
                        } else {
                            progressDialog.dismiss();
                            Snackbar.make(view, "Gagal Upload Data, error tidak diketahui", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(view, getResources().getString(R.string.error) + e.getMessage().toString(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
