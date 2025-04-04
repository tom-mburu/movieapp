package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.adapters.discovermovieAdapter;
import com.example.movieapp.adapters.movieAdapter;
import com.example.movieapp.interfaces.OnItemClickListener;
import com.example.movieapp.models.discovermoviemodel;
import com.example.movieapp.models.movieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link moviesfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class moviesfragment extends Fragment implements OnItemClickListener {
    String url;
    String id=null;
    RequestQueue queueMovies;
    RecyclerView discovermore;
    JSONArray[] trends=new JSONArray[1];
    RecyclerView trending;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  public  ArrayList<movieModel> firsteight=new ArrayList<>();
  public  ArrayList<discovermoviemodel> remainingMovieObjects=new ArrayList<>();

    public moviesfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment moviesfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static moviesfragment newInstance(String param1, String param2) {
        moviesfragment fragment = new moviesfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moviesfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trending=view.findViewById(R.id.trendingMovies);
        discovermore=view.findViewById(R.id.discovermore);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        url="https://api.themoviedb.org/3/trending/movie/week?key=876e8b74c92c2e146b1db2705e1ee322";
        queueMovies=Volley.newRequestQueue(getActivity());
        //get json response for trending movies
        //Json Request
        JsonObjectRequest trendingMovies = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            trends[0] = response.getJSONArray("results");
                            for (int i = 0; i < trends[0].length(); i++) {
                                movieModel model = new movieModel();
                                JSONObject object = trends[0].getJSONObject(i);
                                int movieId=object.getInt("id");
                                String rating = Float.valueOf(object.getString("vote_average")).toString();
                                String title = object.getString("title");
                                String backdropurl = "https://image.tmdb.org/t/p/original/".concat(object.getString("backdrop_path"));
                                String releaseDate = object.getString("release_date");
                                model.setRating(rating);
                                model.setMovieid(movieId);
                                model.setBackdropImg(backdropurl);
                                model.setTitle(title);
                                model.setReleaseDate(releaseDate);
                                firsteight.add(model);

                                if (i == 8) {
                                    break;
                                }


                            }
                            if(firsteight.size()>0){
                                Toast.makeText(getActivity(),new String(String.valueOf(firsteight.size())),Toast.LENGTH_LONG).show();
                                movieAdapter movieadapter=new movieAdapter(firsteight,getActivity(),moviesfragment.this);
                                trending.setAdapter(movieadapter);
                                // Setup layout manager for items with orientation
// Also supports `LinearLayoutManager.HORIZONTAL`
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                        LinearLayoutManager.HORIZONTAL, false);
// Optionally customize the position you want to default scroll to
                                layoutManager.scrollToPosition(0);
// Attach layout manager to the RecyclerView
                                trending.setLayoutManager(layoutManager);

                                //for discover movie

                                for(int counter=9;counter<trends[0].length();counter++){

                                   // Toast.makeText(getActivity(),String.valueOf(count),Toast.LENGTH_LONG).show();
                                    //fetch the remaining json objects
                                   JSONObject Dobject = trends[0].getJSONObject(counter);
                                    discovermoviemodel discoverdMovie=new discovermoviemodel();

                                    String Dtitle=Dobject.getString("title");
                                    String Dbackdropimage="https://image.tmdb.org/t/p/original/".concat(Dobject.getString("backdrop_path"));
                                    String Ddate=Dobject.getString("release_date");
                                    String Doverview=Dobject.getString("overview");
                                    String Drating=Float.valueOf(Dobject.getString("vote_average")).toString();
                                    discoverdMovie.setDate(Ddate);
                                    discoverdMovie.setBackdropImage(Dbackdropimage);
                                    discoverdMovie.setOverview(Doverview);
                                    discoverdMovie.setRating(Drating);
                                    discoverdMovie.setTitle(Dtitle);
                                    remainingMovieObjects.add(discoverdMovie);

                                }
                                if(remainingMovieObjects.size()>0){
                                    discovermovieAdapter Dadapter=new discovermovieAdapter(getActivity(),remainingMovieObjects,moviesfragment.this);
                                    discovermore.setAdapter(Dadapter);
                                    LinearLayoutManager DlayoutManager = new LinearLayoutManager(getActivity(),
                                            LinearLayoutManager.VERTICAL, false);
// Optionally customize the position you want to default scroll to
                                    layoutManager.scrollToPosition(0);
                                    discovermore.setLayoutManager(DlayoutManager);
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "error getting \ndata from api" + "\n" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                } ){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String,String> params=new HashMap<>();
                //params.put("key","7ab462270865ae3cf6ab77db719f610d");
                String creds=String.format("%s:%s","tom_mburu","38946086");
                params.put("accept","application/json");

                String auth="Bearer "+ "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NzZlOGI3NGM5MmMyZTE0NmIxZGIyNzA1ZTFlZTMyMiIsIm5iZiI6MTczMjgxOTU5NC42MzU5MzkxLCJzdWIiOiI2NzQ2ZTc1NTI5Y2EwZWVhMzA1MDcyN2QiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.uO4_JEIb82HCExRvU8jQ0dkdcYvURpYcihRBmmeMz2k";
                params.put("Authorization",auth);
              return params;
            }
        };
        //add request to queue
        queueMovies.add(trendingMovies);



    }

    @Override
    public void onRecyclerviewItemClick(int position, View view) {
        Toast.makeText(getActivity(), "you clicked "+firsteight.get(position).getTitle()+"\nid is "+String.valueOf(firsteight.get(position).getMovieid()), Toast.LENGTH_LONG).show();
         id=Integer.valueOf(firsteight.get(position).getMovieid()).toString();
         if(id!=null) {
             String detailsurl = "https://api.themoviedb.org/3/movie/" + id + "?" + "key=876e8b74c92c2e146b1db2705e1ee322";
             Intent intent = new Intent(getActivity(), moviedetails.class);
             intent.putExtra("url", detailsurl);
             id=null;
             startActivity(intent);
         }


    }
}