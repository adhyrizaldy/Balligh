package com.exomatik.balligh.balligh.Activity.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Activity.ActSplashScreen;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelUser;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActSignUp extends AppCompatActivity {
    private String jenisAkun = "Muballigh";
    private RelativeLayout bgJenis1, bgJenis2, bgJenis3;
    private RelativeLayout btnJenis1, btnJenis2, btnJenis3, btnJenis4;
    private TextView btnSignIn;
    private Button btnSignUp;
    private EditText etNama, etEmail, etPhone, etPass, etConfirmPass;
    private ProgressDialog progressDialog;
    private View v;
    private String data1;
    private String data2;
    private String data3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnJenis1 = (RelativeLayout) findViewById(R.id.rl_ld);
        btnJenis2 = (RelativeLayout) findViewById(R.id.rl_pm);
        btnJenis3 = (RelativeLayout) findViewById(R.id.rl_mb);
        btnJenis4 = (RelativeLayout) findViewById(R.id.rl_ms);
        bgJenis1 = (RelativeLayout) findViewById(R.id.rl_ld);
        bgJenis2 = (RelativeLayout) findViewById(R.id.rl_pm);
        bgJenis3 = (RelativeLayout) findViewById(R.id.rl_mb);
        btnSignIn = (TextView) findViewById(R.id.text_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        etNama = (EditText) findViewById(R.id.et_nama);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPhone = (EditText) findViewById(R.id.et_nomor);
        etPass = (EditText) findViewById(R.id.et_password);
        etConfirmPass = (EditText) findViewById(R.id.et_confirm_pass);
        v = (View) findViewById(android.R.id.content);

        data1 = getResources().getString(R.string.jenis_akun_1);
        data2 = getResources().getString(R.string.jenis_akun_2);
        data3 = getResources().getString(R.string.jenis_akun_3);
        jenisAkun = gantiBg(data3);

        btnJenis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenisAkun = gantiBg(data1);
            }
        });

        btnJenis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenisAkun = gantiBg(data2);
            }
        });

        btnJenis3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenisAkun = gantiBg(data3);
            }
        });

        btnJenis4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(v, getResources().getString(R.string.error_fitur_not_ready), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActSignUp.this, ActSignIn.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekEditText();
            }
        });
    }

    private String gantiBg(String jenis) {
        switch (jenis) {
            case "Lembaga Dakwah":
                bgJenis1.setBackgroundResource(R.drawable.background_blue_rounded);
                bgJenis2.setBackgroundResource(R.drawable.background_white_rounded);
                bgJenis3.setBackgroundResource(R.drawable.background_white_rounded);
                break;
            case "Pengurus Masjid":
                bgJenis1.setBackgroundResource(R.drawable.background_white_rounded);
                bgJenis2.setBackgroundResource(R.drawable.background_blue_rounded);
                bgJenis3.setBackgroundResource(R.drawable.background_white_rounded);
                break;
            case "Muballigh":
                bgJenis1.setBackgroundResource(R.drawable.background_white_rounded);
                bgJenis2.setBackgroundResource(R.drawable.background_white_rounded);
                bgJenis3.setBackgroundResource(R.drawable.background_blue_rounded);
                break;
        }
        return jenis;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActSignUp.this, ActLanding.class));
        finish();
    }

    private void cekEditText() {
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String pass = etPass.getText().toString();
        String confirmPass = etConfirmPass.getText().toString();

        if (nama.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()
                || !pass.equals(confirmPass) || phone.length() < 9) {
            if (nama.isEmpty()) {
                etNama.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (email.isEmpty()) {
                etEmail.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (phone.isEmpty()) {
                etPhone.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (pass.isEmpty()) {
                etPass.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (confirmPass.isEmpty()) {
                etConfirmPass.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (!pass.equals(confirmPass)) {
                etConfirmPass.setError(getResources().getString(R.string.error_pass_not_same));
            }
            if (phone.length() < 9) {
                etPhone.setError(getResources().getString(R.string.error_nomor_tidak_cukup));
            }
        } else {
            progressDialog = new ProgressDialog(ActSignUp.this);
            progressDialog.setMessage(getResources().getString(R.string.progress_title1));
            progressDialog.setTitle(getResources().getString(R.string.progress_text1));
            progressDialog.setCancelable(false);
            progressDialog.show();
            cekEmaildanPassword(nama, email, phone, pass, jenisAkun);
        }
    }

    private void cekEmaildanPassword(final String nama, final String email, final String phone, final String pass, final String jenisAkun) {
        Query query = FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("noHp")
                .equalTo(phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
                    etPhone.setError(getResources().getString(R.string.error_nomor_terdaftar));
                    Snackbar snackbar = Snackbar
                            .make(v, getResources().getString(R.string.error_nomor_terdaftar), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    prosesDaftar(nama, email, phone, pass, jenisAkun);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(ActSignUp.this, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prosesDaftar(final String nama, final String email, final String phone, String pass, final String jenis) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addUsertoFirebase(task.getResult().getUser(), nama, email, phone, jenis);
                } else {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(v, getResources().getString(R.string.error_unknown), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                if (e.getMessage().toString().contains("email address is already in use")) {
                    etEmail.setError(getResources().getString(R.string.error_email_terdaftar));
                    Snackbar snackbar = Snackbar
                            .make(v, getResources().getString(R.string.error_email_terdaftar), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(v, getResources().getString(R.string.error) + e.getMessage().toString(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    private void addUsertoFirebase(FirebaseUser firebaseUser, final String nama,
                                   final String email, final String phone, final String jenis) {
        final ModelUser userData = new ModelUser(nama, email, phone, firebaseUser.getUid(), null, jenis);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("users")
                .child(phone)
                .setValue(userData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            saveData(email);
                        } else {
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(v, getResources().getString(R.string.error) + task.getException().getMessage().toString(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ActSignUp.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(final String email) {
        sendVerificationEmail();
    }

    private void sendVerificationEmail() {
        FirebaseAuth.getInstance().getCurrentUser()
                .sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ActSignUp.this, "Silahkan verifikasi email " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActSignUp.this, ActSplashScreen.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActSignUp.this, "Gagal mengirim email verifikasi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
