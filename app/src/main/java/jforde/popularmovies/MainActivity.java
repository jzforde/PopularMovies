package jforde.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import jforde.popularmovies.view.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), MainActivity.this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            viewPager.setAdapter(mSectionsPagerAdapter);

        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // save refernce to view pager
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // tab titles
        private String tabTitles[] = new String[]{"Most Popular", "Top Rated"};
        private Context context;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: position = 0;
                    Log.i(TAG, "IN POSITION " + position);
                    return new PopularMoviesFragment();

                case 1: position = 1;
                    Log.i(TAG,  "IN POSITION " + position );
                    return new TopMoviesFragment();

            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorites:
                Intent favActivity = new Intent(this, FavoriteMoviesActivity.class);
                startActivity(favActivity);

        }
        return super.onOptionsItemSelected(item);
    }


}

