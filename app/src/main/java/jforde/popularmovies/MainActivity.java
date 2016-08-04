package jforde.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

        if (savedInstanceState == null) {
            Fragment mPopularMoviesFragment = new PopularMoviesFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_holder, mPopularMoviesFragment).commit();
        }
        /*
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), MainActivity.this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            viewPager.setAdapter(mSectionsPagerAdapter);

        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // save refernce to view pager
        tabLayout.setupWithViewPager(viewPager);
        */
    }
    /*
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
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorites:
                Intent favActivity = new Intent(this, FavoriteMoviesFragment.class);
                startActivity(favActivity);
                break;
            case R.id.popular:
                Intent popMoviesActivity = new Intent(this, PopularMoviesActivity.class);
                startActivity(popMoviesActivity);
                break;
            case R.id.top_rated:
                Intent topRateMoviesActivity = new Intent(this, TopRatedMoviesActivity.class);
                startActivity(topRateMoviesActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

