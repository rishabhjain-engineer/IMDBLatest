package com.example.lenovo.imdblatest.tabs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.lenovo.imdblatest.R;
import com.example.lenovo.imdblatest.model.Movie;
import com.example.lenovo.imdblatest.network.MySingleton;

import java.util.ArrayList;

/**
 * Created by ravi on 26/11/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> listOfMovies;

    public MoviesAdapter(Context context, ArrayList<Movie> listOfMovies) {
        this.context = context;
        this.listOfMovies = listOfMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(listOfMovies.get(position).getTitle());
        holder.popularity.setText("" + listOfMovies.get(position).getPopularity());
        holder.poster.setImageUrl(listOfMovies.get(position).getPosterPath()
                , MySingleton.getInstance(context).getImageLoader());

    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView poster;
        private TextView title, popularity;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = (NetworkImageView) itemView.findViewById(R.id.poster);
            title = (TextView) itemView.findViewById(R.id.title);
            popularity = (TextView) itemView.findViewById(R.id.popularity);
        }
    }

}
