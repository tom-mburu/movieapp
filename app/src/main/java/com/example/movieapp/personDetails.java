package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class personDetails extends AppCompatActivity {
    String url=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        CircleImageView profile=findViewById(R.id.personpic);
        TextView personName=findViewById(R.id.name);
        TextView Biography=findViewById(R.id.biography);
        TextView knownfor=findViewById(R.id.knownfor);
        TextView placeofbirth=findViewById(R.id.placeofbirth);
        TextView dateofbirth=findViewById(R.id.dateofbirth);
        Intent intent=getIntent();
        RequestQueue queue= Volley.newRequestQueue(this);
       if(intent!=null){
            url=intent.getStringExtra("url");
            if(url!=null){
                //hit the api
                JsonObjectRequest persondetailrequest=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       //populate the ui with data from the api
                        try {
                            String profilepiclink="https://image.tmdb.org/t/p/original/"+response.getString("profile_path");
                            String name=response.getString("name");
                            String biography=response.getString("biography");
                            String info=response.getString("known_for_department");
                            String PlaceOfBirth=response.getString("place_of_birth");
                            String DateOfBirth=response.getString("birthday");
                            Picasso.get().load(profilepiclink).into(profile);
                           personName.setText(name);
                            Biography.setText(biography);
                            knownfor.setText(info);
                            placeofbirth.setText(PlaceOfBirth);
                            dateofbirth.setText(DateOfBirth);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(personDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                queue.add(persondetailrequest);

            }
        }
    }
}