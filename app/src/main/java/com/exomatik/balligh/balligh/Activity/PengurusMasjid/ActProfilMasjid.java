package com.exomatik.balligh.balligh.Activity.PengurusMasjid;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.balligh.balligh.Activity.LembagaDakwah.ActEditProfilLembaga;
import com.exomatik.balligh.balligh.Activity.LembagaDakwah.ActMainLembaga;
import com.exomatik.balligh.balligh.Featured.UserPreference;
import com.exomatik.balligh.balligh.R;
import com.squareup.picasso.Picasso;
import com.xw.repo.BubbleSeekBar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActProfilMasjid extends AppCompatActivity {
    private ImageView btnBack, btnEdit;
    private TextView textJumlahKutbah, textJumlahCeramah;
    private TextView textNamaLembaga, textAlamat, textPengurus, textNamaPengurus, textGelarPengurus;
    private CircleImageView imgUser, imgPengurus;
    private UserPreference userPreference;
    private View view;
    private BubbleSeekBar seekBar1, seekBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profil_masjid);

        btnBack = (ImageView) findViewById(R.id.img_back);
        btnEdit = (ImageView) findViewById(R.id.img_edit);
        textNamaLembaga = (TextView) findViewById(R.id.text_nama_lembaga);
        textAlamat = (TextView) findViewById(R.id.text_alamat);
        textPengurus = (TextView) findViewById(R.id.text_pengurus);
        textNamaPengurus = (TextView) findViewById(R.id.text_nama);
        textGelarPengurus = (TextView) findViewById(R.id.text_gelar);
        textJumlahKutbah = (TextView) findViewById(R.id.text_jumlah_khutbah);
        textJumlahCeramah = (TextView) findViewById(R.id.text_jumlah_ceramah);
        imgUser = (CircleImageView) findViewById(R.id.img_user);
        imgPengurus = (CircleImageView) findViewById(R.id.img_pengurus);
        view = (View) findViewById(android.R.id.content);
        seekBar1 = (BubbleSeekBar) findViewById(R.id.seek_bar1);
        seekBar2 = (BubbleSeekBar) findViewById(R.id.seek_bar2);

        userPreference = new UserPreference(this);
        setData();
        seekBar1.setEnabled(false);
        seekBar2.setEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActProfilMasjid.this, ActMainMasjid.class));
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActProfilMasjid.this, ActEditProfilMasjid.class));
                finish();
            }
        });
    }

    private void setData() {
        if (userPreference.getKEY_FOTO() != null) {
            Uri localUri = Uri.parse(userPreference.getKEY_FOTO());
            Picasso.with(this).load(localUri).into(imgUser);
        } else {
            imgUser.setImageResource(R.drawable.logo_balligh);
        }
        textPengurus.setPaintFlags(textPengurus.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActProfilMasjid.this, ActMainMasjid.class));
        finish();
    }
}
