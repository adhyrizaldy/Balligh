package com.exomatik.balligh.balligh.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.MainActivity;
import com.exomatik.balligh.balligh.Model.ModelUser;
import com.exomatik.balligh.balligh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ActSplashScreen extends AppCompatActivity {
    private TextView textService;
    private boolean back = false;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textService = (TextView) findViewById(R.id.text_maintenance);

        userPreference = new UserPreference(this);

        getDataMaintenance();
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
                    saveData(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                }
                else {
                    Intent homeIntent = new Intent(ActSplashScreen.this, ActLanding.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, 2000L);
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

                            startActivity(new Intent(ActSplashScreen.this, MainActivity.class));
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