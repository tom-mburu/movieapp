package com.example.movieapp;

import android.content.Intent;
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
import com.example.movieapp.adapters.peopleAdapter;
import com.example.movieapp.interfaces.OnItemClickListener;
import com.example.movieapp.models.personModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link peoplefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class peoplefragment extends Fragment implements OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    ArrayList<personModel> persons=new ArrayList<>();
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerviewPeople;

    public peoplefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment peoplefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static peoplefragment newInstance(String param1, String param2) {
        peoplefragment fragment = new peoplefragment();
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
        return inflater.inflate(R.layout.fragment_peoplefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         recyclerviewPeople=view.findViewById(R.id.people);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RequestQueue queuePeople= Volley.newRequestQueue(getActivity().getApplicationContext());
        String url="https://api.themoviedb.org/3/trending/person/week?key=876e8b74c92c2e146b1db2705e1ee322";
        JsonObjectRequest peoplerequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getActivity(),"i got response",Toast.LENGTH_LONG).show();
                try {
                    JSONArray trendingPeople=response.getJSONArray("results");
                    for(int x=0;x<trendingPeople.length();x++){
                        personModel model=new personModel();
                        JSONObject personObj=trendingPeople.optJSONObject(x);
                        String profilePicLink="https://image.tmdb.org/t/p/original/"+personObj.getString("profile_path");
                        int id=personObj.getInt("id");
                        String name=personObj.getString("name");
                        String info=personObj.getString("known_for_department");
                        model.setId(id);
                        model.setProfilepiclink(profilePicLink);
                        model.setProfilename(name);
                        model.setKnownfor(info);
                        persons.add(model);
                    }
                    if(persons.size()>0){

                        peopleAdapter adapter=new peopleAdapter(getActivity(),persons,peoplefragment.this);
                        recyclerviewPeople.setAdapter(adapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.VERTICAL, false);
// Optionally customize the position you want to default scroll to
                        layoutManager.scrollToPosition(0);
                        recyclerviewPeople.setLayoutManager(layoutManager);

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
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
        queuePeople.add(peoplerequest);

    }

    @Override
    public void onRecyclerviewItemClick(int position, View view) {
       // Toast.makeText(getActivity(),"you clicked "+String.valueOf(persons.get(position).getProfilename()),Toast.LENGTH_LONG).show();
        String url="https://api.themoviedb.org/3/person/"+String.valueOf(persons.get(position).getId())+"?key=876e8b74c92c2e146b1db2705e1ee322";
        Intent intent=new Intent(getActivity(),personDetails.class);

        intent.putExtra("url",url);
        startActivity(intent);

    }
}