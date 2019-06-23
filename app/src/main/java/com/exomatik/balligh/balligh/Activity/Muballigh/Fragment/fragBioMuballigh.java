package com.exomatik.balligh.balligh.Activity.Muballigh.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Activity.Muballigh.ActProfilMuballigh;
import com.exomatik.balligh.balligh.Data.DataLokasi;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelBiodataMuballigh;
import com.exomatik.balligh.balligh.Model.ModelUser;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragBioMuballigh extends Fragment {
    public static String nama;
    private View view, v;
    private CircleImageView btnFoto, btnAlamat;
    private EditText etNama, etNomor, etTempatLahir, etTanggalLahir, etAlamat;
    private RadioGroup rgJk;
    private Spinner spinnerProv, spinnerKota;
    private RelativeLayout btnUpdate;
    private ArrayList<String> listProvinsi = new ArrayList<String>();
    private ArrayList<String> listKota = new ArrayList<String>();
    private final DataLokasi data = new DataLokasi();
    private ProgressDialog progressDialog;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int PLACE_PICKER_REQUEST = 1;
    private int PICK_IMAGE = 100;
    private String locationPoint;
    private UserPreference userPreference;
    private Uri imageUri = null;
    private StorageReference mStorageRef;
    private boolean cekSpinner = false;

    public fragBioMuballigh() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_bio_muballigh, container, false);

        btnFoto = (CircleImageView) view.findViewById(R.id.img_user);
        etNama = (EditText) view.findViewById(R.id.et_nama);
        etNomor = (EditText) view.findViewById(R.id.et_nomor);
        rgJk = (RadioGroup) view.findViewById(R.id.rg_jk);
        etTempatLahir = (EditText) view.findViewById(R.id.et_tempat_lahir);
        etTanggalLahir = (EditText) view.findViewById(R.id.et_tanggal_lahir);
        etAlamat = (EditText) view.findViewById(R.id.et_alamat);
        spinnerProv = (Spinner) view.findViewById(R.id.spinner_provinsi);
        spinnerKota = (Spinner) view.findViewById(R.id.spinner_kota);
        btnAlamat = (CircleImageView) view.findViewById(R.id.img_pick_alamat);
        btnUpdate = (RelativeLayout) view.findViewById(R.id.rl_update);
        v = (View) view.findViewById(android.R.id.content);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
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

        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTanggalLahir.setError(null);
                Toast.makeText(getActivity(), "Klik 2 kali", Toast.LENGTH_SHORT).show();
                getDate();
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
                getFoto();
            }
        });

        return view;
    }

    private void setEditTextData() {
        etNama.setText(nama);
        etNomor.setText(userPreference.getKEY_PHONE());
        if (userPreference.getKEY_FOTO() != null) {
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(getActivity()).load(localUri).into(btnFoto);
        } else {
            btnFoto.setImageResource(R.drawable.logo_balligh);
        }

        Query query = FirebaseDatabase.getInstance()
                .getReference(userPreference.getKEY_JENIS())
                .child("Biodata")
                .orderByChild("nomorHP")
                .equalTo(userPreference.getKEY_PHONE());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelBiodataMuballigh dataKelas = snapshot.getValue(ModelBiodataMuballigh.class);
                        etTanggalLahir.setText(dataKelas.getTanggalLahir());
                        etTempatLahir.setText(dataKelas.getTempatLahir());
                        etAlamat.setText(dataKelas.getAlamat());
                        locationPoint = dataKelas.getLocationPoint();
                        if (dataKelas.getJk().equals(getResources().getString(R.string.et_jk_laki))){
                            rgJk.check(R.id.rb_jk1);
                        }
                        else if (dataKelas.getJk().equals(getResources().getString(R.string.et_jk_perempuan))){
                            rgJk.check(R.id.rb_jk2);
                        }
                        listProvinsi.removeAll(listProvinsi);
                        listProvinsi.add(dataKelas.getProvinsi());

                        ArrayList<String> prov = data.namaProvinsi();

                        for (int a = 0; a < prov.size(); a++){
                            if (!prov.get(a).equals(dataKelas.getProvinsi())){
                                listProvinsi.add(prov.get(a));
                            }
                        }

                        ArrayAdapter<String> dataNilai = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_text_hitam, listProvinsi);
                        spinnerProv.setAdapter(dataNilai);

                        listKota.removeAll(listKota);
                        ArrayList<String> Kota = data.namaKota(dataKelas.getProvinsi());

                        listKota.add(dataKelas.getKota());
                        Toast.makeText(getActivity(), "Kota" + dataKelas.getKota(), Toast.LENGTH_SHORT).show();

                        for (int a = 0; a < Kota.size(); a++){
                            if (!Kota.get(a).equals(dataKelas.getKota())){
                                listKota.add(Kota.get(a));
                            }
                        }

                        ArrayAdapter<String> dataKota = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_text_hitam, listKota);
                        spinnerKota.setAdapter(dataKota);
                    }
                }
                else {
                    cekSpinner = true;
                    setSpinnerProvinsi();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    private void getDate() {
        Calendar localCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                Calendar localCalendar = Calendar.getInstance();
                localCalendar.set(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
                etTanggalLahir.setText(dateFormatter.format(localCalendar.getTime()));
            }
        }, localCalendar.get(Calendar.YEAR)
                , localCalendar.get(Calendar.MONTH)
                , localCalendar.get(Calendar.DATE));
        datePickerDialog.show();
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

        if ((resultCode == -1) && (requestCode == PICK_IMAGE)) {
            imageUri = data.getData();
            Picasso.with(getActivity()).load(imageUri).into(btnFoto);
        }
    }

    private void cekForm() {
        String nama = etNama.getText().toString();
        String phone = etNomor.getText().toString();
        String tempatLahir = etTempatLahir.getText().toString();
        String tanggalLahir = etTanggalLahir.getText().toString();
        String alamat = etAlamat.getText().toString();
        String prov = listProvinsi.get(spinnerProv.getSelectedItemPosition());
        String kota = listKota.get(spinnerKota.getSelectedItemPosition());
        int jk = rgJk.getCheckedRadioButtonId();

        if (nama.isEmpty() || phone.isEmpty() || tempatLahir.isEmpty() || tanggalLahir.isEmpty() || alamat.isEmpty()
                || jk == -1 || prov.length() == 1 || kota.length() == 1|| (imageUri == null && userPreference.getKEY_FOTO() == null)
                || etNomor.length() < 9
                ) {
            if (nama.isEmpty()) {
                etNama.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (phone.isEmpty()) {
                etNomor.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (tempatLahir.isEmpty()) {
                etTempatLahir.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (tanggalLahir.isEmpty()) {
                etTanggalLahir.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (alamat.isEmpty()) {
                etAlamat.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (jk == -1) {
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error_jk_not_found), Snackbar.LENGTH_LONG);
                snackbar.show();
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
            if (imageUri == null && userPreference.getKEY_FOTO() == null) {
                Snackbar snackbar = Snackbar
                        .make(view, "Anda belum mengupload Foto", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {
            RadioButton rb = (RadioButton) view.findViewById(jk);
            String jenisKelamin = rb.getText().toString();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getResources().getString(R.string.progress_title1));
            progressDialog.setTitle(getResources().getString(R.string.progress_text1));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();

            cekNomor(nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, prov, kota);
        }
    }

    private void cekNomor(final String nama, final String phone, final String tempatLahir, final String tanggalLahir
            , final String alamat, final String jenisKelamin, final String provinsi, final String kotaPilihan) {
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
                            uploadMethod(nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, provinsi, kotaPilihan);
                        }
                        else {
                            progressDialog.dismiss();
                            etNomor.setError(getResources().getString(R.string.error_nomor_terdaftar));
                            Snackbar snackbar = Snackbar
                                    .make(view, getResources().getString(R.string.error_nomor_terdaftar), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                } else {
                    uploadMethod(nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, provinsi, kotaPilihan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.error) + databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadMethod(String nama, String phone, String tempatLahir, String tanggalLahir, String alamat
            , String jenisKelamin, String provinsi, String kotaPilihan) {
        if (userPreference.getKEY_FOTO() == null) {
            simpanFoto(nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, provinsi, kotaPilihan, imageUri);
        } else {
            if (imageUri == null) {
                simpanData(null, nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, provinsi, kotaPilihan);
            } else {
                hapusFoto(nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, provinsi, kotaPilihan, imageUri);
            }
        }
    }

    private void hapusFoto(final String nama, final String phone, final String tempatLahir, final String tanggalLahir, final String alamat
            , final String jenisKelamin, final String provinsi, final String kotaPilihan, final Uri imageUri) {
        StorageReference fotoDelete = getInstance().getReferenceFromUrl(userPreference.getKEY_FOTO());
        fotoDelete.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                simpanFoto(nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, provinsi, kotaPilihan, imageUri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error) + e.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void simpanFoto(final String nama, final String phone, final String tempatLahir
            , final String tanggalLahir, final String alamat, final String jenisKelamin, final String provinsi
            , final String kotaPilihan, final Uri image) {
        mStorageRef.child("fotoMuballigh/" + image.getLastPathSegment()).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            public void onSuccess(UploadTask.TaskSnapshot paramAnonymousTaskSnapshot) {
                simpanData(paramAnonymousTaskSnapshot, nama, phone, tempatLahir, tanggalLahir, alamat, jenisKelamin, provinsi, kotaPilihan);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception paramAnonymousException) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error) + paramAnonymousException.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage(Integer.toString((int) progress) + " %");
                progressDialog.setProgress((int) progress);
                String progressText = taskSnapshot.getBytesTransferred() / 1024 + "KB/" + taskSnapshot.getTotalByteCount() / 1024 + "KB";
                progressDialog.setTitle(progressText);
            }
        });
    }

    private void simpanData(UploadTask.TaskSnapshot uriFoto, final String nama, final String phone
            , String tempatLahir, String tanggalLahir, String alamat, String jenisKelamin
            , String provinsi, String kotaPilihan) {
        String foto = null;
        if (uriFoto == null) {
            foto = userPreference.getKEY_FOTO();
        } else {
            foto = uriFoto.getDownloadUrl().toString();
        }

        ModelBiodataMuballigh data = new ModelBiodataMuballigh(nama, phone, jenisKelamin
                , tempatLahir, tanggalLahir, provinsi, kotaPilihan, alamat, foto, locationPoint);

        hapusDataUser(userPreference.getKEY_PHONE(), data);
    }

    private void hapusDataUser(final String phone, final ModelBiodataMuballigh data) {
        DatabaseReference dv_remove = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(phone);
        dv_remove.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                setDataUser(phone, data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error) + e.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void setDataUser(final String phone, final ModelBiodataMuballigh data) {
        ModelUser dataUser = new ModelUser(userPreference.getKEY_NAME(), userPreference.getKEY_EMAIL(), data.getNomorHP(),
                userPreference.getKEY_UID(), userPreference.getKEY_JENIS());

        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference
                .child("users")
                .child(data.getNomorHP())
                .setValue(dataUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> paramAnonymous2Task) {
                        if (paramAnonymous2Task.isSuccessful()) {
                            hapusBioUser(phone, data);
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

    private void hapusBioUser(String phone, final ModelBiodataMuballigh data) {
        DatabaseReference dv_remove = FirebaseDatabase.getInstance()
                .getReference()
                .child(getResources().getString(R.string.jenis_akun_3))
                .child(getResources().getString(R.string.text_frag_biodata))
                .child(phone);
        dv_remove.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                setBioUser(data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(view, getResources().getString(R.string.error) + e.getMessage().toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void setBioUser(final ModelBiodataMuballigh data) {
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference
                .child(getResources().getString(R.string.jenis_akun_3))
                .child(getResources().getString(R.string.text_frag_biodata))
                .child(data.getNomorHP())
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> paramAnonymous2Task) {
                        if (paramAnonymous2Task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Berhasil Upload Data", Toast.LENGTH_LONG).show();
                            userPreference.setKEY_PHONE(data.getNomorHP());
                            userPreference.setKEY_FOTO(data.getFoto());
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
