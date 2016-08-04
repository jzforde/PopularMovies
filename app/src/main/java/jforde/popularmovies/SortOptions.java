package jforde.popularmovies;

public interface SortOptions {
     void sortByPopularity(MovieResponseListener movieResponseListener);
     void sortByRating(MovieResponseListener movieResponseListener);
}
