package jforde.popularmovies;

import java.util.List;

/**
 * Created by jillianforde on 8/3/16.
 */

public interface MovieResponseListener {
    void onSuccess(List<Movie> movies);
}

