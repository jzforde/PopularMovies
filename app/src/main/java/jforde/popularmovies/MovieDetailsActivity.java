package jforde.popularmovies;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by jillianforde on 6/18/16.
 */

public class MovieDetailsActivity extends AppCompatActivity{
    public static final String EXTRA_NAME = "movie_name";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_POPULARITY = "popularity";
    public static final String EXTRA_VOTE_COUNT = "vote_count";
    public static final String EXTRA_POSTER_NAME = "poster_img";
    public static final String EXTRA_BACKDROP_NAME = "backdrop_path";
    public static final String EXTRA_OVERVIEW = "movie_overview";
    public static final String EXTRA_VOTE_AVG = "vote_avg";
    public static final String EXTRA_VOTE_AVG_FOR_DB = "voteavg";
    public static String EXTRA_RELEASE_DATE = "release_date";
    private final String TAG = "MovieDetailsActivity";
    String moviePosterImg;
    private CoordinatorLayout mCoordinatorLayout;

    private String movieOverview;
    private String movieRatingTxt;
    private double voteAverage;
    private String movieTitle;
    private String releaseDateTxt;
    int id;
    double popularity;
    String backdropPath;
    int voteCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.movie_details);
        Intent intent = getIntent();
        TextView overview = (TextView) findViewById(R.id.overview);
        TextView releaseDate = (TextView) findViewById(R.id.release_date);
        TextView movieRtng = (TextView) findViewById(R.id.rating);

        movieTitle = intent.getStringExtra(EXTRA_NAME);
        moviePosterImg = intent.getStringExtra(EXTRA_POSTER_NAME);
        movieOverview = intent.getStringExtra(EXTRA_OVERVIEW);
        movieRatingTxt = intent.getStringExtra(EXTRA_VOTE_AVG);
        voteAverage = intent.getDoubleExtra(EXTRA_VOTE_AVG_FOR_DB, 0.0);
        releaseDateTxt = intent.getStringExtra(EXTRA_RELEASE_DATE);
        id = intent.getIntExtra(EXTRA_ID, 0);
        popularity = intent.getDoubleExtra(EXTRA_POPULARITY, 0.0);
        backdropPath = intent.getStringExtra(EXTRA_BACKDROP_NAME);
        voteCount = intent.getIntExtra(EXTRA_VOTE_COUNT, 0);

        movieRtng.setText(movieRatingTxt);
        overview.setText(movieOverview);
        releaseDate.setText(releaseDateTxt);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movieTitle);
        loadBackdrop();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mark_as_fav_btn);
        Drawable noFillHeart = getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp);
        int heartColor = getResources().getColor(R.color.white);
        noFillHeart.setColorFilter(heartColor, PorterDuff.Mode.SRC_IN);

    }

    private void loadPoster(){
        //final ImageView poster = (ImageView) findViewById(R.id.details_poster);
    }
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(moviePosterImg).centerCrop().into(imageView);
        Log.i(TAG, "Loading backdrop");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    public void markAsFavorite(final View view){
        Snackbar successfullyMarkedAsFav = Snackbar.make
                (mCoordinatorLayout, "Added to favorites", Snackbar.LENGTH_LONG);
        successfullyMarkedAsFav.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
