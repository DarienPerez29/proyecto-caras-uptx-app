package com.example.proyectocarasuptx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class InfoActivity extends AppCompatActivity {

    ImageView backBtn, facebookBtn, twitterBtn, instagramBtn, youtubeBtn;
    private static final String FB_URL = "https://www.facebook.com/UPTxOficial/";
    private static final String TW_URL = "https://twitter.com/UPTxOficial";
    private static final String IG_URL = "https://www.instagram.com/uptxoficial/";
    private static final String YT_URL = "https://www.youtube.com/channel/UCY_jx-W3WBuGkYY1GRgBsLg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        backBtn = findViewById(R.id.back_btn);
        facebookBtn = findViewById(R.id.facebook_btn);
        twitterBtn = findViewById(R.id.twitter_btn);
        instagramBtn = findViewById(R.id.instagram_btn);
        youtubeBtn = findViewById(R.id.youtube_btn);

        backBtn.setOnClickListener(v -> onBackPressed());

        facebookBtn.setOnClickListener(v -> gotoUrl(FB_URL));
        twitterBtn.setOnClickListener(v -> gotoUrl(TW_URL));
        instagramBtn.setOnClickListener(v -> gotoUrl(IG_URL));
        youtubeBtn.setOnClickListener(v -> gotoUrl(YT_URL));
    }

    private void gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}