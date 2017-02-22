package com.codepath.flickster;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.codepath.flickster.Config.YOUTUBE_API_KEY;

/**
 * Created by YichuChen on 2/16/17.
 */

public class PlayYoutubeActivity extends YouTubeBaseActivity {

    //public static final String YOUTUBE_API_KEY = "AIzaSyBNVRHLXzhWIoKKYaMLgQIpWaF8rf5TM7U";
    private YouTubePlayerView youTubePlayerView;
    private String mVideoId;
    private String mVideoKey;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_play_youtube);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);



        prepbundle();
        connectvideo();
    }


   private void prepbundle(){
       Bundle bundle = getIntent().getExtras();
       mVideoId = bundle.getString("videoId");

   }
    private void connectvideo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(getApiUrl(mVideoId), params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", throwable.toString());
                Toast.makeText(PlayYoutubeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                mVideoKey = gson.fromJson(responseString, GsonYoutube.class).results.get(0).key;
               //mVideoKey = gson.fromJson(responseString, GsonMovie.class).results.get(0).id;
                //mData.results.get(position).id
                //launch youtube player if load api success
                loadYoutube(Config.YOUTUBE_API_KEY);

            }



        });
    }

    private void loadYoutube(String key) {
        youTubePlayerView.initialize(key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.cueVideo(mVideoKey);
            }

            @Override
            public void onInitializationFailure(
                    YouTubePlayer.Provider provider,
                    YouTubeInitializationResult youTubeInitializationResult) {
                //Log.e("ERROR",);

            }
        });
    }

    private String getApiUrl(String videoId) {
        return "https://api.themoviedb.org/3/movie/" + videoId + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    }
}