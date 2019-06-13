package com.exomatik.balligh.balligh.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelUser;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ActSignIn extends AppCompatActivity {
    private EditText etPhone, etPassword;
    private Button btnSignIn;
    private ProgressDialog progressDialog;
    private View v;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        etPhone = (EditText) findViewById(R.id.et_nomor);
        etPassword = (EditText) findViewById(R.id.et_password);
        v = (View) findViewById(android.R.id.content);

        userPreference = new UserPreference(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekEditText();
            }
        });
    }

    private void cekEditText() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();

        if (phone.isEmpty() || password.isEmpty()){
            if (phone.isEmpty()){
                etPhone.setError(getResources().getString(R.string.error_data_kosong));
            }
            if (password.isEmpty()){
                etPassword.setError(getResources().getString(R.string.error_data_kosong));
            }
        }
        else {
            progressDialog = new ProgressDialog(ActSignIn.this);
            progressDialog.setMessage(getResources().getString(R.string.progress_title1));
            progressDialog.setTitle(getResources().getString(R.string.progress_text1));
            progressDialog.setCancelable(false);
            progressDialog.show();
            getDataEmail(phone, password);
        }
    }

    private void getDataEmail(String phone, final String password) {
        Query query = FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("noHp")
                .equalTo(phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ModelUser user = null;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ModelUser data = snapshot.getValue(ModelUser.class);
                        user = data;
                    }
                    prosesLogin(user, password);
                }
                else {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(v, getResources().getString(R.string.error_phone_not_found), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(ActSignIn.this, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prosesLogin(final ModelUser user, String pass) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.getEmail(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    saveData(user.getEmail());
                }else {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(v, "Mohon maaf, " + task.getException().getMessage().toString() , Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                if (e.getMessage().toString().contains("There is no user record")){
                    etPhone.setError(getResources().getString(R.string.error_phone_not_found));
                }
                Snackbar snackbar = Snackbar
                        .make(v, "Mohon maaf, " + e.getMessage().toString() , Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void saveData(final String email){
        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator localIterator = dataSnapshot.getChildren().iterator();
                    while (localIterator.hasNext()) {
                        ModelUser localDataUser = (ModelUser) ((DataSnapshot) localIterator.next()).getValue(ModelUser.class);
                        if (localDataUser.getEmail().toString().equalsIgnoreCase(email)){
                            userPreference.setKEY_NAME(localDataUser.getNamaLengkap());
                            userPreference.setKEY_EMAIL(localDataUser.getEmail());
                            userPreference.setKEY_PHONE(localDataUser.getNoHp());
                            userPreference.setKEY_UID(localDataUser.getUid());
                            userPreference.setKEY_FOTO(localDataUser.getFoto());
                            userPreference.setKEY_JENIS(localDataUser.getJenisAkun());

                            Toast.makeText(ActSignIn.this, getResources().getString(R.string.toast_success_login), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActSignIn.this, ActSplashScreen.class));
                            finish();
                        }
                    }
                }
                else {
                    Toast.makeText(ActSignIn.this, getResources().getString(R.string.error_update), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ActSignIn.this, getResources().getString(R.string.error_update), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActSignIn.this, ActLanding.class));
        finish();
    }
}
