package com.exomatik.balligh.balligh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.balligh.balligh.R;

public class ActSignUp extends AppCompatActivity {
    private int jenisAkun = 3;
    private ImageView bgJenis1, bgJenis2, bgJenis3;
    private RelativeLayout btnJenis1, btnJenis2, btnJenis3;
    private TextView btnSignIn;
    private Button btnSignUp;
    private EditText etNama, etEmail, etPhone, etPass, etConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnJenis1 = (RelativeLayout) findViewById(R.id.rl_ld);
        btnJenis2 = (RelativeLayout) findViewById(R.id.rl_pm);
        btnJenis3 = (RelativeLayout) findViewById(R.id.rl_mb);
        bgJenis1 = (ImageView) findViewById(R.id.bg_jenis_ld);
        bgJenis2 = (ImageView) findViewById(R.id.bg_jenis_pm);
        bgJenis3 = (ImageView) findViewById(R.id.bg_jenis_mb);
        btnSignIn = (TextView) findViewById(R.id.text_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        etNama = (EditText) findViewById(R.id.et_nama);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPhone  = (EditText) findViewById(R.id.et_nomor);
        etPass = (EditText) findViewById(R.id.et_password);
        etConfirmPass = (EditText) findViewById(R.id.et_confirm_pass);

        jenisAkun = gantiBg(3);

        btnJenis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiBg(1);
            }
        });

        btnJenis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiBg(2);
            }
        });

        btnJenis3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiBg(3);
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

    private void cekEditText() {
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String pass = etPass.getText().toString();
        String confirmPass = etConfirmPass.getText().toString();

        if (nama.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()){
            if (nama.isEmpty()){
                etNama.setError(getResources().getString(R.string.text_data_kosong));
            }
            if (email.isEmpty()){
                etEmail.setError(getResources().getString(R.string.text_data_kosong));
            }
            if (phone.isEmpty()){
                etPhone.setError(getResources().getString(R.string.text_data_kosong));
            }
            if (pass.isEmpty()){
                etPass.setError(getResources().getString(R.string.text_data_kosong));
            }
            if (confirmPass.isEmpty()){
                etConfirmPass.setError(getResources().getString(R.string.text_data_kosong));
            }
        }
        else {
            Toast.makeText(this, "TIdak kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private int gantiBg(int jenis){
        switch (jenis){
            case 1 :
                bgJenis1.setImageResource(R.drawable.background_blue_rounded);
                bgJenis2.setImageResource(R.drawable.background_white_rounded);
                bgJenis3.setImageResource(R.drawable.background_white_rounded);
                jenis = 1;
                break;
            case 2 :
                bgJenis1.setImageResource(R.drawable.background_white_rounded);
                bgJenis2.setImageResource(R.drawable.background_blue_rounded);
                bgJenis3.setImageResource(R.drawable.background_white_rounded);
                jenis = 2;
                break;
            case 3 :
                bgJenis1.setImageResource(R.drawable.background_white_rounded);
                bgJenis2.setImageResource(R.drawable.background_white_rounded);
                bgJenis3.setImageResource(R.drawable.background_blue_rounded);
                jenis = 3;
                break;
        }
        return jenis;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActSignUp.this, ActLanding.class));
        finish();
    }
}
