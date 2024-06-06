package com.example.videosharingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubePlayerActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    EditText videoUrlEt;
    String videoId;
    SignInCredential credential;
    final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);
        credential = getIntent().getParcelableExtra("CREDENTIAL");

        TextView nameTv = findViewById(R.id.userNameTV);
        ImageView avatarView = findViewById(R.id.avatarImage);
        if (credential != null) {
            nameTv.setText(credential.getDisplayName());
            Picasso.with(this).load(credential.getProfilePictureUri()).into(avatarView);
        }

        //initialising the GUI widgets for Video Player and user input
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.setEnableAutomaticInitialization(false);
        videoUrlEt = findViewById(R.id.ytVideoUrlEt);
        Button playBtn = findViewById(R.id.ytPlayVideoBtn);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // calling method to play video
                playVideoButtonClick();
            }
        });
        //YouTubePlayer is a lifecycle aware widget, Add a lifecycle observer
        //so the video only plays when it is visible to the user
        getLifecycle().addObserver(youTubePlayerView);

        //initialising the YouTubePlayerView and load the default video to play
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            public void onReady(@NonNull YouTubePlayer youTubePlayer){
                super.onReady(youTubePlayer);
                videoId = "tBv-4ttoyvc";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
    public String extractVideoIdFromUrl(String url){
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        // extract the VideoID and returns it
        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        return null;
    }

    private String youTubeLinkWithoutProtocolAndDomain(String url) {
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);
        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return null;
    }

    private void playVideoButtonClick() {
        //check if the user has entered a video url
        String urlStr = videoUrlEt.getText().toString();
        //if they haven't and it's empty, load the default video
        if(urlStr.isEmpty()){
            videoId = "tBv-4ttoyvc";
        } else{
            //otherwise, get the VideoID from the URL entered and store it in videoId
            videoId = extractVideoIdFromUrl(urlStr);
        }
        if(videoId!=null)
            youTubePlayerView.getYouTubePlayerWhenReady(this::playVideo);
        else
            //otherwise , let the user know the URL is not correct
            Toast.makeText(this, "Enter a valid YouTube vide URL to  play a video", Toast.LENGTH_LONG).show();
    }
    //method to play the video in the Video Player
    private void playVideo(YouTubePlayer player){
        player.loadVideo(videoId, 0);
    }
}