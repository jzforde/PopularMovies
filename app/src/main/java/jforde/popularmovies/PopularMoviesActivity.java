package jforde.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jillianforde on 7/21/16.
 */

public class PopularMoviesActivity extends AppCompatActivity{
    FragmentHelper mFragmentHelper = new FragmentHelper();
    PopularMoviesFragment mPopularMoviesFragment = new PopularMoviesFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentHelper.setupFragment(mPopularMoviesFragment);
    }


}
