package jforde.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jforde.popularmovies.R;

/**
 * Created by jillianforde on 6/18/16.
 */

public class MovieDetailsActivity extends AppCompatActivity{
    public static final String EXTRA_NAME = "movie_name";
    public static final String EXTRA_POSTER_NAME = "poster_img";
    public static final String EXTRA_OVERVIEW = "movie_overview";
    public static final String EXTRA_VOTE_AVG = "vote_avg";
    public static String EXTRA_RELEASE_DATE = "release_date";
    String moviePosterImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent = getIntent();
        TextView overview = (TextView) findViewById(R.id.overview);
        TextView releaseDate = (TextView) findViewById(R.id.release_date);
        TextView movieRtng = (TextView) findViewById(R.id.rating);
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);
        moviePosterImg = intent.getStringExtra(EXTRA_POSTER_NAME);
        final String movieOverview = intent.getStringExtra(EXTRA_OVERVIEW);
        final String movieRatingTxt = intent.getStringExtra(EXTRA_VOTE_AVG);
        final String releaseDateTxt = intent.getStringExtra(EXTRA_RELEASE_DATE);
        movieRtng.setText(movieRatingTxt);
        overview.setText(movieOverview);
        releaseDate.setText(releaseDateTxt);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);
        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(moviePosterImg).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
