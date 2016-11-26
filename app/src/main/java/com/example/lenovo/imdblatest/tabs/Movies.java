package com.example.lenovo.imdblatest.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lenovo.imdblatest.R;
import com.example.lenovo.imdblatest.constants.Constants;
import com.example.lenovo.imdblatest.model.Movie;
import com.example.lenovo.imdblatest.network.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Movies extends Fragment {

    public static final String TAG = "RAVI";

    private RecyclerView moviesList;
    private ArrayList<Movie> listOfMovies;

    public Movies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        moviesList = (RecyclerView) view.findViewById(R.id.moviesList);
        moviesList.setHasFixedSize(true);
        moviesList.setLayoutManager(new LinearLayoutManager(getActivity()));

        listOfMovies = new ArrayList<>();

        getData();

        return view;
    }


    /**
     * This method will get movies list from the api
     */
    void getData() {

        listOfMovies.clear();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=e8f6d477ebd6c06dfe40a9bd82e5c56e&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray results = response.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);

                        Movie movie = new Movie();
                        movie.setId(object.getDouble("id"));
                        movie.setTitle(object.getString("title"));
                        movie.setOverview(object.getString("overview"));
                        movie.setPopularity(object.getDouble("popularity"));
                        movie.setPosterPath(Constants.POSTER_URL + object.getString("poster_path"));

                        listOfMovies.add(movie);

                    }

                    MoviesAdapter adapter = new MoviesAdapter(getActivity(), listOfMovies);
                    moviesList.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(request);


    }

}
