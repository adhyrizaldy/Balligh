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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Activity.Muballigh.ActProfilMuballigh;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelBiodataMuballigh;
import com.exomatik.balligh.balligh.Model.ModelPendidikan;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragPendMuballigh extends Fragment{
    private View view;
    private EditText etNamaS1, etKotaS1, etTahunS1, etJurusanS1, etGelarS1;
    private EditText etNamaS2, etKotaS2, etTahunS2, etJurusanS2, etGelarS2;
    private EditText etNamaS3, etKotaS3, etTahunS3, etJurusanS3, etGelarS3;
    private RelativeLayout btnUpdate;
    private ProgressDialog progressDialog;
    private UserPreference userPreference;

    public fragPendMuballigh() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_pend_muballigh, container, false);

        btnUpdate = (RelativeLayout) view.findViewById(R.id.rl_update);
        etNamaS1 = (EditText) view.findViewById(R.id.et_nama_pt);
        etKotaS1 = (EditText) view.findViewById(R.id.et_kota_pt);
        etTahunS1 = (EditText) view.findViewById(R.id.et_tahun_pt);
        etJurusanS1 = (EditText) view.findViewById(R.id.et_jurusan_pt);
        etGelarS1 = (EditText) view.findViewById(R.id.et_gelar_pt_s1);
        etNamaS2 = (EditText) view.findViewById(R.id.et_nama_pt_s2);
        etKotaS2 = (EditText) view.findViewById(R.id.et_kota_pt_s2);
        etTahunS2 = (EditText) view.findViewById(R.id.et_tahun_pt_s2);
        etJurusanS2 = (EditText) view.findViewById(R.id.et_jurusan_pt_s2);
        etGelarS2 = (EditText) view.findViewById(R.id.et_gelar_pt_s2);
        etNamaS3 = (EditText) view.findViewById(R.id.et_nama_pt_s3);
        etKotaS3 = (EditText) view.findViewById(R.id.et_kota_pt_s3);
        etTahunS3 = (EditText) view.findViewById(R.id.et_tahun_pt_s3);
        etJurusanS3 = (EditText) view.findViewById(R.id.et_jurusan_pt_s3);
        etGelarS3 = (EditText) view.findViewById(R.id.et_gelar_pt_s3);

        userPreference = new UserPreference(getActivity());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                progressDialog.setTitle(getResources().getString(R.string.progress_text1));
                progressDialog.setCancelable(false);
                progressDialog.show();
                cekEditText();
            }
        });


        return view;
    }

    private void cekEditText() {
        String namaS1 = etNamaS1.getText().toString();
        String kotaS1 = etKotaS1.getText().toString();
        String tahunS1 = etTahunS1.getText().toString();
        String jurusanS1 = etJurusanS1.getText().toString();
        String gelarS1 = etGelarS1.getText().toString();

        String namaS2 = etNamaS2.getText().toString();
        String kotaS2 = etKotaS2.getText().toString();
        String tahunS2 = etTahunS2.getText().toString();
        String jurusanS2 = etJurusanS2.getText().toString();
        String gelarS2 = etGelarS2.getText().toString();

        String namaS3 = etNamaS3.getText().toString();
        String kotaS3 = etKotaS3.getText().toString();
        String tahunS3 = etTahunS3.getText().toString();
        String jurusanS3 = etJurusanS3.getText().toString();
        String gelarS3 = etGelarS3.getText().toString();

        ArrayList<ModelPendidikan> listPT = new ArrayList<ModelPendidikan>();
        listPT.add(new ModelPendidikan("S1", namaS1, kotaS1, tahunS1, jurusanS1, gelarS1));
        listPT.add(new ModelPendidikan("S2", namaS2, kotaS2, tahunS2, jurusanS2, gelarS2));
        listPT.add(new ModelPendidikan("S3", namaS3, kotaS3, tahunS3, jurusanS3, gelarS3));

        setPendidikanUser(listPT);
    }

    private void setPendidikanUser(final ArrayList<ModelPendidikan> data) {
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference
                .child(getResources().getString(R.string.jenis_akun_3))
                .child(getResources().getString(R.string.text_frag_pendidikan))
                .child(userPreference.getKEY_PHONE())
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> paramAnonymous2Task) {
                        if (paramAnonymous2Task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Berhasil Upload Data", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getActivity(), ActProfilMuballigh.class));
                            getActivity().finish();
                            return;
                        }
                        progressDialog.dismiss();
                        Snackbar.make(view, "Gagal Upload Data", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}
