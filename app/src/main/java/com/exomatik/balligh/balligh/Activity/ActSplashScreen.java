package com.exomatik.balligh.balligh.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textService = (TextView) findViewById(R.id.text_maintenance);

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
                    Toast.makeText(ActSplashScreen.this, "Sudah login", Toast.LENGTH_SHORT).show();
//                    saveData(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                }
                else {
                    Intent homeIntent = new Intent(ActSplashScreen.this, ActLanding.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, 2000L);
    }
}