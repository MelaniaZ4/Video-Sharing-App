package com.example.videosharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.SignInCredential;

public class MainActivity extends AppCompatActivity {

    SignInCredential credential;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWatchVideo = findViewById(R.id.watchVideoBtn);
        Button btnListChannel = findViewById(R.id.listChannelBtn);

        btnWatchVideo.setOnClickListener(view -> watchVideoFunction());

        btnListChannel.setOnClickListener(view -> listChannelFunction());
    }

    private void listChannelFunction() {
        // Navigate to YouTubeChannelListActivity
    }

    private void watchVideoFunction() {
        Intent intent = new Intent(MainActivity.this, YouTubePlayerActivity.class);
        SignInCredential credential = getIntent().getParcelableExtra("CREDENTIAL");
        if (credential != null) {
            intent.putExtra("CREDENTIAL", credential);
            startActivity(intent);
        } else {
            Log.e("MainActivity", "Credential is null");
        }
    }
}