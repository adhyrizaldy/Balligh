package com.exomatik.balligh.balligh.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Activity.Authentication.ActLanding;
import com.exomatik.balligh.balligh.Activity.Authentication.ActWelcome;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.Model.ModelUser;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ActSplashScreen extends AppCompatActivity {
    private TextView textService;
    private boolean back = false;
    private UserPreference userPreference;
    private Button btnRefresh, btnSignOut, btnSend;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_screen);

        textService = (TextView) findViewById(R.id.text_maintenance);
        btnRefresh = (Button) findViewById(R.id.btn_refresh);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSignOut = (Button) findViewById(R.id.btn_keluar);

        userPreference = new UserPreference(this);
        btnSend.setVisibility(View.GONE);
        btnSignOut.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ActSplashScreen.this);
                progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                progressDialog.setTitle(getResources().getString(R.string.progress_text1));
                progressDialog.setCancelable(false);
                progressDialog.show();
                FirebaseAuth.getInstance().signOut();
                hapusUser();
                startActivity(new Intent(ActSplashScreen.this, ActSplashScreen.class));
                finish();
                progressDialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ActSplashScreen.this);
                progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                progressDialog.setTitle(getResources().getString(R.string.progress_text1));
                progressDialog.setCancelable(false);
                progressDialog.show();
                sendVerificationEmail();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ActSplashScreen.this);
                progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                progressDialog.setTitle(getResources().getString(R.string.progress_text1));
                progressDialog.setCancelable(false);
                progressDialog.show();
                refreshCekEmail();
            }
        });

        getDataMaintenance();
    }

    private void refreshCekEmail() {
        FirebaseAuth.getInstance().getCurrentUser()
                .reload()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        startActivity(new Intent(ActSplashScreen.this, ActSplashScreen.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ActSplashScreen.this, "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationEmail() {
        FirebaseAuth.getInstance().getCurrentUser()
                .sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(ActSplashScreen.this, "Silahkan verifikasi email " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ActSplashScreen.this, "Gagal mengirim email verifikasi", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (back){
            finish();
        }
    }

    private void getDataMaintenance() {
        FirebaseDatabase.getInstance().getReference("maintenance").addListenerForSingleValueEvent(this.valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            back = true;
            Toast.makeText(ActSplashScreen.this, paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext()) {
                    String localDataString = (String) ((DataSnapshot) localIterator.next()).getValue(String.class);

                    if (localDataString.equals("aktif")){
                        appsActive();
                    }
                    else if (localDataString.equals("maintenance")){
                        textService.setVisibility(View.VISIBLE);
                        textService.setText("Mohon maaf, aplikasi sedang maintenance");
                        back = true;
                    }
                    else if (localDataString.equals("service")){
                        textService.setVisibility(View.VISIBLE);
                        textService.setText("Mohon maaf, aplikasi akan maintenance segera");

                        appsActive();
                    }
                }
            }
        }
    };

    private void appsActive(){
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    cekEmailVerification();
                }
                else {
                    Intent homeIntent = new Intent(ActSplashScreen.this, ActLanding.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, 2000L);
    }

    private void cekEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()){
            saveData(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }else {
            btnSend.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRefresh.setVisibility(View.VISIBLE);
        }
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

                            startActivity(new Intent(ActSplashScreen.this, ActWelcome.class));
                            finish();
                        }
                    }
                }
                else {
                    Toast.makeText(ActSplashScreen.this, getResources().getString(R.string.error_update), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ActSplashScreen.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}