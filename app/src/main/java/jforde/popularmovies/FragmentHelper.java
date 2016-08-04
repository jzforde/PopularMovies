package jforde.popularmovies;

import android.support.v4.app.Fragment;

/**
 * Created by jillianforde on 7/26/16.
 */

public class FragmentHelper {
    private Fragment mFragment = new Fragment();

    public Fragment setupFragment(Fragment fragment){
        mFragment = fragment;
        return mFragment;
    }
}
