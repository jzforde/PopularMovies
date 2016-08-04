package jforde.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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



import static jforde.popularmovies.TopMoviesFragment.RecyclerViewAdapter.ViewHolder.VHTAG;

/**
 * Created by jillianforde on 6/19/16.
 */

public class TopMoviesFragment extends Fragment{
    private static MovieSorter movieSorter = new MovieSorter();
    private String TAG = "TopMoviesFragment";
    RecyclerView rv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(
                R.layout.top_movies_fragment, container, false);
        new TopMoviesTask().execute();
        Log.i(TAG, " onCreateView just happened");
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        Log.i(TAG, "SETTING UP RECYCLER VIEW");
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(new RecyclerViewAdapter(getActivity(),
                movieSorter.topRatedMovies));
    }

    private class TopMoviesTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            movieSorter.sortByRating(null);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            setupRecyclerView(rv);
        }
    }



    public static class RecyclerViewAdapter
            extends RecyclerView.Adapter<TopMoviesFragment.RecyclerViewAdapter.ViewHolder> {

        private String TAG = "RecyclerViewAdapter";
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

        public RecyclerViewAdapter(Context context, List<Movie> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(VHTAG, "TopMoviesFragment:  IN ON CREATE VIEW HOLDER");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_poster, parent, false);
            Log.i(VHTAG, "The View is--> " + view);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = movieSorter.topRatedMovies.get(position).getTitle();
            holder.overview = movieSorter.topRatedMovies.get(position).getOverview();
            holder.release_date = movieSorter.topRatedMovies.get(position).getRelease_date();
            holder.poster = movieSorter.topRatedMovies.get(position).getPoster_path();
            holder.vote_average = Double.toString(movieSorter.topRatedMovies.get(position).getVote_average());
            Log.i(TAG, "onBindViewHolder just happened ");
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(MovieDetailsActivity.EXTRA_POSTER_NAME, holder.poster);
                    intent.putExtra(MovieDetailsActivity.EXTRA_NAME, holder.mBoundString);
                    intent.putExtra(MovieDetailsActivity.EXTRA_OVERVIEW,holder.overview);
                    intent.putExtra(MovieDetailsActivity.EXTRA_RELEASE_DATE, holder.release_date);
                    intent.putExtra(MovieDetailsActivity.EXTRA_VOTE_AVG, holder.vote_average);
                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(holder.poster)
                    .fitCenter()
                    .into(holder.mImageView);

            Log.i(TAG, "Gliding them top movies");
        }


        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
