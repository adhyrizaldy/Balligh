package com.exomatik.balligh.balligh.Activity.PengurusMasjid.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Data.DataLokasi;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelUser;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragProfilMasjid extends Fragment {
    public static String namaPengurus, fotoPengurus, namaLembaga;
    private View view, v;
    private CircleImageView btnFoto, btnAlamat, btnFotoPengurus;
    private EditText etNama, etNomor, etAlamat, etNamaPengurus, etDesc;
    private Spinner spinnerProv, spinnerKota;
    private RelativeLayout btnUpdate;
    private ArrayList<String> listProvinsi = new ArrayList<String>();
    private ArrayList<String> listKota = new ArrayList<String>();
    private final DataLokasi data = new DataLokasi();
    private ProgressDialog progressDialog;
    private int PLACE_PICKER_REQUEST = 1;
    private int PICK_IMAGE = 100;
    private String locationPoint;
    private UserPreference userPreference;
    private Uri imageUriLembaga = null, imageUriPengurus = null;
    private StorageReference mStorageRef;
    private boolean cekSpinner = false;
    private int requestFoto = 0;

    public fragProfilMasjid() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_profil_lembaga, container, false);

        btnFoto = (CircleImageView) view.findViewById(R.id.img_user);
        btnFotoPengurus = (CircleImageView) view.findViewById(R.id.img_pengurus);
        etNama = (EditText) view.findViewById(R.id.et_nama);
        etNomor = (EditText) view.findViewById(R.id.et_nomor);
        etAlamat = (EditText) view.findViewById(R.id.et_alamat);
        etDesc = (EditText) view.findViewById(R.id.et_desc);
        etNamaPengurus  = (EditText) view.findViewById(R.id.et_nama_pengurus);
        spinnerProv = (Spinner) view.findViewById(R.id.spinner_provinsi);
        spinnerKota = (Spinner) view.findViewById(R.id.spinner_kota);
        btnAlamat = (CircleImageView) view.findViewById(R.id.img_pick_alamat);
        btnUpdate = (RelativeLayout) view.findViewById(R.id.rl_update);
        v = (View) view.findViewById(android.R.id.content);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        userPreference = new UserPreference(getActivity());

        setEditTextData();
        cekSpinner = false;

        spinnerProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cekSpinner){
                    Log.e("Jalan", "jalan");
                    listKota.removeAll(listKota);
                    setSpinnerKota(position);
                }
                cekSpinner = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAlamat.setError(null);
                getLocation();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekForm();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFoto = 1;
                getFoto();
            }
        });

        btnFotoPengurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFoto = 2;
                getFoto();
            }
        });

        return view;
    }

    private void setEditTextData() {
        etNamaPengurus.setText(namaPengurus);
        etNomor.setText(userPreference.getKEY_PHONE());
        etNama.setText(namaLembaga);

        if (userPreference.getKEY_FOTO() != null) {
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(getActivity()).load(localUri).into(btnFoto);
        } else {
            btnFoto.setImageResource(R.drawable.logo_balligh);
        }

        if (fotoPengurus != null) {
            Uri localUri = Uri.parse(fotoPengurus);
            Picasso.with(getActivity()).load(localUri).into(btnFotoPengurus);
        } else {
            btnFotoPengurus.setImageResource(R.drawable.logo_balligh);
        }

        setSpinnerProvinsi();
    }

    private void setSpinnerKota(int position) {
        listKota.add("-");
        listKota = data.namaKota(listProvinsi.get(position));
        ArrayAdapter<String> dataKota = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_text_hitam, listKota);
        spinnerKota.setAdapter(dataKota);
    }

    private void setSpinnerProvinsi() {
        listProvinsi.removeAll(listProvinsi);
        listProvinsi.add("-");
        listProvinsi = data.namaProvinsi();

        ArrayAdapter<String> dataNilai = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_text_hitam, listProvinsi);
        spinnerProv.setAdapter(dataNilai);
    }

    private void getLocation() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(getActivity(), getResources().getString(R.string.error) + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getActivity(), getResources().getString(R.string.error) + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getFoto() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.progress_title1));
        progressDialog.setTitle(getResources().getString(R.string.progress_text1));
        progressDialog.setCancelable(false);
        progressDialog.show();
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
        progressDialog.dismiss();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                etAlamat.setText(place.getName());
                locationPoint = place.getLatLng().toString();
            }
        }

        if (requestFoto == 1){
            if ((resultCode == -1) && (requestCode == PICK_IMAGE)) {
                imageUriLembaga = data.getData();
                Picasso.with(getActivity()).load(imageUriLembaga).into(btnFoto);
            }
        }
        else if (requestFoto == 2){
            if ((resultCode == -1) && (requestCode == PICK_IMAGE)) {
                imageUriPengurus = data.getData();
                Picasso.with(getActivity()).load(imageUriLembaga).into(btnFotoPengurus);
            }
        }
    }

    private void cekForm() {
        String namaLembaga = etNama.getText().toString();
        String namaPengurus = etNamaPengurus.getText().toString();
        String desc = etDesc.getText().toString();
        String phone = etNomor.getText().toString();
        String alamat = etAlamat.getText().toString();
        String prov = listProvinsi.get(spinnerProv.getSelectedItemPosition());
        String kota = listKota.get(spinnerKota.getSelectedItemPosition());

        if (namaLembaga.isEmpty() || namaPengurus.isEmpty() || namaPengurus.isEmpty() || phone.isEmpty() || desc.isEmpty()
                || alamat.isEmpty() || prov.length() == 1 || kota.length() == 1 || phone.length() < 9
                || (imageUriLembaga == null && userPreference.getKEY_FOTO() == null)
                || (imageUriPengurus == null && fotoPengurus == null)
                ) {
            if (namaLembaga.isEmpty()) {
                etNama.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (phone.isEmpty()) {
                etNomor.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (namaPengurus.isEmpty()) {
                etNamaPengurus.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (desc.isEmpty()) {
                etDesc.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (alamat.isEmpty()) {
                etAlamat.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (prov.length() == 1) {
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error_prov_not_found), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            if (kota.length() == 1) {
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error_kota_not_found), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            if (phone.length() < 9) {
                etNomor.setError(getResources().getString(R.string.error_nomor_tidak_cukup));
            }
            if (imageUriLembaga == null && userPreference.getKEY_FOTO() == null) {
                Snackbar snackbar = Snackbar
                        .make(view, "Anda belum mengupload Foto Lembaga", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            if (imageUriPengurus == null && fotoPengurus == null) {
                Snackbar snackbar = Snackbar
                        .make(view, "Anda belum mengupload Foto Pengurus", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        else {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getResources().getString(R.string.progress_title1));
            progressDialog.setTitle(getResources().getString(R.string.progress_text1));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();

            cekNomor(namaLembaga, namaPengurus, phone, desc, alamat, prov, kota);
        }
    }

    private void cekNomor(final String namaLembaga, final String namaPengurus, final String phone, final String desc, final String alamat
            , final String prov, final String kota) {
        Query query = FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("noHp")
                .equalTo(phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ModelUser data = snapshot.getValue(ModelUser.class);

                        if (data.getNoHp().equals(userPreference.getKEY_PHONE())){
//                            uploadMethod(namaLembaga, namaPengurus, phone, desc, alamat, prov, kota);
                        }
                        else {
                            progressDialog.dismiss();
                            etNomor.setError(getResources().getString(R.string.error_nomor_terdaftar));
                            Snackbar snackbar = Snackbar
                                    .make(v, getResources().getString(R.string.error_nomor_terdaftar), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                } else {
                    uploadMethod(namaLembaga, namaPengurus, phone, desc, alamat, prov, kota);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.error) + databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadMethod(String namaLembaga, String namaPengurus, String phone, String desc, String alamat, String prov, String kota) {

    }
}
