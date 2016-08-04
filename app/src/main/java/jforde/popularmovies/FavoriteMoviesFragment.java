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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jillianforde on 6/19/16.
 */

public class FavoriteMoviesFragment extends Fragment {
    private static final String TAG = "FavoriteMoviesActivity";
    private RecyclerView rv;
    private List<Movie> favoriteMovies = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(R.layout.fragment_all_movies, container, false);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        Log.i(TAG, "SETTING UP RECYCLER VIEW");
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        //TODO Extract data from DB

    }

    public static class FavoriteMoviesAdapter
            extends RecyclerView.Adapter<FavoriteMoviesFragment.FavoriteMoviesAdapter.ViewHolder> {
        final String TAG = "sRecyclerViewAdapter";
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
            }
        }

        public FavoriteMoviesAdapter(Context context) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;

        }

        @Override
        public FavoriteMoviesFragment.FavoriteMoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(FavoriteMoviesFragment.FavoriteMoviesAdapter.ViewHolder.VHTAG, "FavoriteMoviesFragment:  IN ON CREATE VIEW HOLDER");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_poster, parent, false);
            view.setBackgroundResource(mBackground);
            return new FavoriteMoviesFragment.FavoriteMoviesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final FavoriteMoviesFragment.FavoriteMoviesAdapter.ViewHolder holder, int position) {
            final Movie movie = mValues.get(position);
            Log.i(TAG, "In FavoriteMovies onBindViewHolder");
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
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_AVG,Double.toString(movie.getVote_average()));
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_AVG_FOR_DB, movie.getVote_average());
                    intent.putExtra(MovieDetailsActivity.EXTRA_ID, movie.getId());
                    intent.putExtra(MovieDetailsActivity.EXTRA_POPULARITY, movie.getPopularity());
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_COUNT, movie.getVote_count());
                    intent.putExtra(MovieDetailsActivity.EXTRA_BACKDROP_NAME, movie.getBackdrop_path());
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



}
