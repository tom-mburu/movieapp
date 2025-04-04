package com.example.movieapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.adapters.tvshowsAdapter;
import com.example.movieapp.interfaces.OnItemClickListener;
import com.example.movieapp.models.tvshowsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tvshowsfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tvshowsfragment extends Fragment implements OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RequestQueue queue;
    ArrayList<tvshowsModel> shows=new ArrayList<>();
    RecyclerView tvshowslist;

    public tvshowsfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tvshowsfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static tvshowsfragment newInstance(String param1, String param2) {
        tvshowsfragment fragment = new tvshowsfragment();
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
        return inflater.inflate(R.layout.fragment_tvshowsfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queue= Volley.newRequestQueue(getActivity());
        String url="https://api.themoviedb.org/3/trending/tv/week?key=876e8b74c92c2e146b1db2705e1ee322";
        JsonObjectRequest tvshowsrequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //parse the json response
                try {
                    JSONArray array=response.getJSONArray("results");
                    for(int x=0;x<array.length();x++){
                        JSONObject obj=array.getJSONObject(x);
                        tvshowsModel model=new tvshowsModel();
                        String backdroppath="https://image.tmdb.org/t/p/original/"+obj.getString("backdrop_path");
                        String showname=obj.getString("name");
                        String showoverview=obj.getString("overview");
                        String rating=obj.getString("vote_average");
                        model.setTvshowBackdrop(backdroppath);
                        model.setTvshowname(showname);
                        model.setTvshowOverview(showoverview);
                        model.setRating(Float.valueOf(rating).floatValue());
                        shows.add(model);

                    }
                    if(shows.size()>0){
                        tvshowsAdapter adapter=new tvshowsAdapter(getActivity(),tvshowsfragment.this,shows);
                        tvshowslist.setAdapter(adapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.VERTICAL, false);
// Optionally customize the position you want to default scroll to
                        layoutManager.scrollToPosition(0);
// Attach layout manager to the RecyclerView
                        tvshowslist.setLayoutManager(layoutManager);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }

        ){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                //params.put("key","7ab462270865ae3cf6ab77db719f610d");
                String creds=String.format("%s:%s","tom_mburu","38946086");
                params.put("accept","application/json");

                String auth="Bearer "+ "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NzZlOGI3NGM5MmMyZTE0NmIxZGIyNzA1ZTFlZTMyMiIsIm5iZiI6MTczMjgxOTU5NC42MzU5MzkxLCJzdWIiOiI2NzQ2ZTc1NTI5Y2EwZWVhMzA1MDcyN2QiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.uO4_JEIb82HCExRvU8jQ0dkdcYvURpYcihRBmmeMz2k";
                params.put("Authorization",auth);
                return params;
            }
        };
        queue.add(tvshowsrequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvshowslist=view.findViewById(R.id.tvshows);
    }

    @Override
    public void onRecyclerviewItemClick(int position, View view) {
Toast.makeText(getActivity(),shows.get(position).getTvshowname(),Toast.LENGTH_LONG).show();
    }

}