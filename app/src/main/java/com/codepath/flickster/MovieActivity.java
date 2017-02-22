package com.codepath.flickster;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private ListView mListView;
    private ListAdapter mListAdapter;
    private ProgressBar progressBar;
    private String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private GsonMovie mData;

    private void setList() {

        mListView = (ListView) findViewById(R.id.lvMovies);
        mListAdapter = new com.codepath.flickster.ListAdapter(this, mData, getScreenWidth());
        mListView.setAdapter(mListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        //String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        //params.put("key", "value");
        //params.put("more", "data");

        client.get(url, params, new TextHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("ERROR", throwable.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MovieActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //super.onSuccess(statusCode, headers, responseString);
                progressBar.setVisibility(View.GONE);
                Gson gson = new Gson();
                mData = gson.fromJson(responseString, GsonMovie.class);
                setList();
            }


        });


    }



    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;

    }
}
