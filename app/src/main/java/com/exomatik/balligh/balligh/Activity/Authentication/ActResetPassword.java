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
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.balligh.balligh.Activity.ActSplashScreen;
import com.exomatik.balligh.balligh.Model.ModelUser;
import com.exomatik.balligh.balligh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActResetPassword extends AppCompatActivity {
    private TextView btnSignUp;
    private EditText etNomor;
    private Button btnKirim;
    private ProgressDialog progressDialog;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reset_password);

        btnSignUp = (TextView) findViewById(R.id.text_sign_up);
        btnKirim = (Button) findViewById(R.id.btn_kirim);
        etNomor = (EditText) findViewById(R.id.et_nomor);
        v = (View) findViewById(android.R.id.content);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomor = etNomor.getText().toString();

                if (nomor.isEmpty()){
                    etNomor.setError(getResources().getString(R.string.error_data_kosong));
                }
                else {
                    progressDialog = new ProgressDialog(ActResetPassword.this);
                    progressDialog.setMessage(getResources().getString(R.string.progress_title1));
                    progressDialog.setTitle(getResources().getString(R.string.progress_text1));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    getEmail(nomor);
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActResetPassword.this, ActSignUp.class));
                finish();
            }
        });
    }

    private void getEmail(String nomor) {
        Query query = FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("noHp")
                .equalTo(nomor);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ModelUser user = null;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ModelUser data = snapshot.getValue(ModelUser.class);
                        user = data;
                    }
                    prosesReset(user);
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
                Toast.makeText(ActResetPassword.this, getResources().getString(R.string.error) + databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prosesReset(final ModelUser user) {
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(user.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ActResetPassword.this, "Silahkan cek email " + user.getEmail() + " untuk mereset password", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(ActResetPassword.this, ActSignIn.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ActResetPassword.this, getResources().getString(R.string.error) + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActResetPassword.this, ActSplashScreen.class));
        finish();
    }
}
