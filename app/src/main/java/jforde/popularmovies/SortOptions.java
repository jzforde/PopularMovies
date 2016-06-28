package jforde.popularmovies;

public interface SortOptions {
     void sortByPopularity(MovieSorter.NetworkListener networkListener);
     void sortByRating(MovieSorter.NetworkListener networkListener);
}
