package jforde.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by jillianforde on 6/19/16.
 */

public class PopularMoviesFragment extends Fragment {
    private String TAG = "PopularMoviesFragment";
    private RecyclerView rv;
    private static MovieSorter movieSorter = new MovieSorter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(R.layout.fragment_all_movies, container, false);
        Log.i(TAG, " onCreateView just happened");
        movieSorter.sortByPopularity(new MovieResponseListener() {
            @Override
            public void onSuccess(List<Movie> movies) {
                setupRecyclerView(rv, movies);
            }
        });
        return rv;
    }

    public void setupRecyclerView(RecyclerView recyclerView, List<Movie> movies) {
        Log.i(TAG, "SETTING UP RECYCLER VIEW");
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), movies));
    }


public static class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {
        final String TAG = "sRecyclerViewAdapter";
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Movie> mValues;
        public static class ViewHolder extends RecyclerView.ViewHolder {
            static String VHTAG = "ViewHolder";
            public String mBoundString;
            private String overview;
            private String poster;
            private String vote_average;
            private String release_date;
            public final View mView;
            public final ImageView mImageView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.poster);
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Movie> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(ViewHolder.VHTAG, "PopularMoviesFragment:  IN ON CREATE VIEW HOLDER");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_poster, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Movie movie = mValues.get(position);
            holder.mBoundString = movieSorter.popularMovies.get(position).getTitle();
            holder.overview = movieSorter.popularMovies.get(position).getOverview();
            holder.release_date = movieSorter.popularMovies.get(position).getRelease_date();
            holder.poster = movieSorter.popularMovies.get(position).getPoster_path();
            holder.vote_average = Double.toString(movieSorter.popularMovies.get(position).getVote_average());
            Log.i(TAG, "In popularMovieFragment onBindViewHolder");
            //TODO: Make Request for videos
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(MovieDetailsActivity.EXTRA_POSTER_NAME, movie.getPoster_path());
                    intent.putExtra(MovieDetailsActivity.EXTRA_NAME, movie.getTitle());
                    intent.putExtra(MovieDetailsActivity.EXTRA_OVERVIEW,movie.getOverview());
                    intent.putExtra(MovieDetailsActivity.EXTRA_RELEASE_DATE, movie.getRelease_date());
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_AVG, holder.vote_average);
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_AVG_FOR_DB, movie.getVote_average());
                    intent.putExtra(MovieDetailsActivity.EXTRA_ID, movie.getId());
                    intent.putExtra(MovieDetailsActivity.EXTRA_POPULARITY, movie.getPopularity());
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_COUNT, movie.getVote_count());
                    intent.putExtra(MovieDetailsActivity.EXTRA_BACKDROP_NAME, movie.getBackdrop_path());
                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(holder.poster)
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }





}
