package com.example.videosharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
        //navigate to YouTubeVideoActivity
    }
}