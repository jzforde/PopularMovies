package jforde.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Making network requests for popular & top rated movies
 * storing movies to a list
 */

public class MovieSorter extends Constants implements SortOptions {
    private static final String TAG = "MovieSorter";
    private final String baseURL = "http://api.themoviedb.org/3/movie/";
    private final String popular = "popular";
    private final String topRated = "top_rated";
    private final String startOfmoviePosterURL = "http://image.tmdb.org/t/p/w185";
    static OkHttpClient client = new OkHttpClient();
    final String popURL = baseURL + popular + apiKey;
    final String topURL = baseURL + topRated + apiKey;
    public ArrayList<Movie> popularMovies = new ArrayList<>();
    public ArrayList<Movie> topRatedMovies = new ArrayList<>();
    private boolean populareRequestMade = false;
    private boolean ratingRequestMade = false;
    NetworkListener mNetworkListener;

    @Override
    public void sortByPopularity(MovieSorter.NetworkListener networkListener) {
        mNetworkListener = networkListener;
        makeRequest(popURL);
        Log.i(TAG, "Sorting by popularity");

    }
    @Override
    public void sortByRating(MovieSorter.NetworkListener networkListener) {
        mNetworkListener = networkListener;
        makeRequest(topURL);
        Log.i(TAG, "SORTING BY RATING");
    }
    public boolean hasRequestBeenMade(String URL){
        switch (URL){
            case topURL:
                if (ratingRequestMade){
                    return true;
                }else {
                    return false;
                }
            case  popURL:
                if(populareRequestMade){
                    return true;
                }else {
                    return false;
                }
        }
        return true;
    }
    private List<Movie> getMovieList(String URL){
        switch (URL) {
            case topURL:
                return topRatedMovies;
            case popURL:
                return popularMovies;
        }
        return null;
    }

    public List<Movie> makeRequest(String URL) {
        if (hasRequestBeenMade(URL)) {
            Log.i(TAG, "Getting movie list");
            getMovieList(URL);
            mNetworkListener.onRequestFinished();
        } else {
            Request request = new Request.Builder()
                    .url(URL)
                    .build();
            okhttp3.Call call = client.newCall(request);
            if(URL == popURL){
                populareRequestMade = true;
            }else {
                ratingRequestMade = true;
            }
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Shit got real");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i(TAG, "In on response");
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            Log.i(TAG, "Success getting movies");
                            JSONObject movieResults = new JSONObject(jsonData);
                            JSONArray movieArr = movieResults.getJSONArray("results");

                            for (int i = 0; i < movieArr.length(); i++) {
                                Movie movie = new Movie();
                                JSONObject m = movieArr.getJSONObject(i);
                                movie.setPoster_path(startOfmoviePosterURL + m.getString("poster_path"));
                                movie.setOverview(m.getString("overview"));
                                movie.setRelease_date(m.getString("release_date"));
                                movie.setId(m.getInt("id"));
                                movie.setTitle(m.getString("title"));
                                movie.setPopularity(m.getDouble("popularity"));
                                movie.setVote_count(m.getInt("vote_count"));
                                movie.setVote_average(m.getDouble("vote_average"));
                                if(populareRequestMade){
                                    popularMovies.add(movie);
                                }else {
                                    topRatedMovies.add(movie);
                                }
                            }
                            //mNetworkListener.onRequestFinished();

                        }else {
                            Log.e(TAG, "Response was unsuccessful");
                        }
                        mNetworkListener.onRequestFinished();
                    } catch (JSONException j) {
                        Log.e(TAG, "Error getting movies");
                    }
                }
            });

        }
        //mNetworkListener.onRequestFinished();
        if (populareRequestMade){
            return popularMovies;
        }else{
            return topRatedMovies;
        }

    }

    public interface NetworkListener{
        void onRequestFinished();

    }
}
