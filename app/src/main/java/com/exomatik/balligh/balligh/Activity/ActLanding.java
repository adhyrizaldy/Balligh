package com.exomatik.balligh.balligh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exomatik.balligh.balligh.R;

public class ActLanding extends AppCompatActivity {
    private Button btnSignIn, btnSignUp;
    private int buttonState = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signIn(){
        startActivity(new Intent(ActLanding.this, ActSignIn.class));
        finish();
    }

    private void signUp(){
        startActivity(new Intent(ActLanding.this, ActSignUp.class));
        finish();
    }
}
