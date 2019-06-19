package com.exomatik.balligh.balligh.Activity.Authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.exomatik.balligh.balligh.Activity.ActMainActivity;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActWelcome extends AppCompatActivity {
    private TextView textUser;
    private CircleImageView imgUser;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);

        textUser = (TextView) findViewById(R.id.text_user);
        imgUser = (CircleImageView) findViewById(R.id.img_user);

        userPreference = new UserPreference(this);
        setData();
    }

    private void setData() {
        if (userPreference.getKEY_FOTO() != null){
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(this).load(localUri).into(imgUser);
        }else {
            imgUser.setImageResource(R.drawable.logo_balligh);
        }

        switch (userPreference.getKEY_JENIS()) {
            case "Muballigh":
                textUser.setText("Ahlan Wa Sahlan Ustadz \n " + userPreference.getKEY_NAME());
                break;
            case "Admin":
                textUser.setText("Ahlan Wa Sahlan Admin \n " + userPreference.getKEY_NAME());
                break;
            case "Lembaga Dakwah":
                textUser.setText("Ahlan Wa Sahlan \n " + userPreference.getKEY_NAME());
                break;
            case "Pengurus Masjid":
                textUser.setText("Ahlan Wa Sahlan \n " + userPreference.getKEY_NAME());
                break;
            case "Masyarakat":
                textUser.setText("Ahlan Wa Sahlan \n " + userPreference.getKEY_NAME());
                break;
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent homeIntent = new Intent(ActWelcome.this, ActMainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, 3000L);
    }

    @Override
    public void onBackPressed() {

    }
}
