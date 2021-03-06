package com.codepath.flickster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    private ImageView imageBackdrop;
    private TextView textTitle;
    private TextView textReleaseDate;
    private RatingBar ratingBar;
    private TextView textOverview;
    private String mImagePath;
    private String mTitle;
    private float mRating;
    private String mReleaseDate;
    private String mTextOverview;
    private String mVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        imageBackdrop = (ImageView) findViewById(R.id.image_backdrop);
        textTitle = (TextView) findViewById(R.id.text_title);
        textReleaseDate = (TextView) findViewById(R.id.text_release_date);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        textOverview = (TextView) findViewById(R.id.text_overview);

        prepbundle();
        setData();
        setListener();
    }



    private void prepbundle() {
        Bundle bundle = getIntent().getExtras();
        mImagePath = bundle.getString("image");
        mTitle = bundle.getString("title");
        mRating = bundle.getFloat("rating") / 2;
        mReleaseDate = bundle.getString("release_date");
        mTextOverview = bundle.getString("overview");
        mVideoId = bundle.getString("videoId");
    }

    private void setData() {

        textTitle.setText(mTitle);
        textReleaseDate.setText(mReleaseDate);
        textOverview.setText(mTextOverview);
        ratingBar.setRating(mRating);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w780" + mImagePath)
                .resize(getScreenWidth(), 0)
                .placeholder(R.drawable.img_placeholder)
                .into(imageBackdrop);
    }

    private void setListener() {
        imageBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoYoutubeActivity();
            }
        });
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private void gotoYoutubeActivity() {
        if (mVideoId != null) {
            Intent intent = new Intent();
            intent.setClass(DetailActivity.this, PlayYoutubeActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("videoId", mVideoId);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}
