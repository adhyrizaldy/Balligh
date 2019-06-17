package com.exomatik.balligh.balligh.Activity.Muballigh;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.balligh.balligh.Activity.ActMainActivity;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActProfilMuballigh extends AppCompatActivity {
    private ImageView btnBack, btnEdit;
    private TextView textHeader;
    private CircleImageView imgUser;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profil_muballigh);

        btnBack = (ImageView) findViewById(R.id.img_back);
        btnEdit = (ImageView) findViewById(R.id.img_edit);
        textHeader = (TextView) findViewById(R.id.text_header);
        imgUser = (CircleImageView) findViewById(R.id.img_user);

        userPreference = new UserPreference(this);

        if (userPreference.getKEY_FOTO() != null){
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(this).load(localUri).into(imgUser);
        }else {
            imgUser.setImageResource(R.drawable.logo_balligh);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActProfilMuballigh.this, ActMainActivity.class));
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActProfilMuballigh.this, ActEditProfilMuballigh.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActProfilMuballigh.this, ActMainActivity.class));
        finish();
    }
}
