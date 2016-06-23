package jforde.popularmovies;

/**
 * Created by jillianforde on 5/7/16.
 */
public interface SortOptions {
    public void sortByPopularity(MovieSorter.NetworkListener networkListener);
    public void sortByRating(MovieSorter.NetworkListener networkListener);
}
