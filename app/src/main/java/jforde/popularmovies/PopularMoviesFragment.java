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

import jforde.popularmovies.view.R;

/**
 * Created by jillianforde on 6/19/16.
 */

public class PopularMoviesFragment extends Fragment implements MovieSorter.NetworkListener{
    static MovieSorter movieSorter = new MovieSorter();
    String TAG = "PopularMoviesFragment";
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(R.layout.fragment_all_movies, container, false);
        movieSorter.sortByPopularity(this);
        Log.i(TAG, " onCreateView just happened");
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        Log.i(TAG, "SETTING UP RECYCLER VIEW");
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                movieSorter.popularMovies));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Movie> mValues;
        public static class ViewHolder extends RecyclerView.ViewHolder {
            static String VHTAG = "ViewHolder";

            public final View mView;
            public final ImageView mImageView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.poster);
                //mTextView = (TextView) view.findViewById(android.R.id.text1);
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Movie> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(ViewHolder.VHTAG, "CheeselistFragment:  IN ON CREATE VIEW HOLDER");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_poster, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Movie movie = mValues.get(position);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(MovieDetailsActivity.EXTRA_POSTER_NAME, movie.getPoster_path());
                    intent.putExtra(MovieDetailsActivity.EXTRA_NAME, movie.getTitle());
                    intent.putExtra(MovieDetailsActivity.EXTRA_OVERVIEW,movie.getOverview());
                    intent.putExtra(MovieDetailsActivity.EXTRA_RELEASE_DATE, movie.getRelease_date());
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_AVG,Double.toString(movie.getVote_average()));
                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(movie.getPoster_path())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    @Override
    public void onRequestFinished() {
        setupRecyclerView(rv);
    }
}
