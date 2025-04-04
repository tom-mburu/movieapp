package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class moviedetails extends AppCompatActivity {
    String url=null;
    RequestQueue queue;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);

        ImageView backdrop=findViewById(R.id.backdrop);
        TextView overview=findViewById(R.id.overview);
        TextView title=findViewById(R.id.title);
        TextView overviewdesc=findViewById(R.id.desc);
        TextView genre=findViewById(R.id.genre);
        LinearLayout genrecontainer=findViewById(R.id.genrecontainer);
        TextView procompanies=findViewById(R.id.procompanies);
        LinearLayout procompaniesecontainer=findViewById(R.id.procompaniescontainer);
        TextView releasedate=findViewById(R.id.releasedate);
        TextView date=findViewById(R.id.date);
        TextView spokenlang=findViewById(R.id.spokenLanguages);
        LinearLayout spokenlangcontainer=findViewById(R.id.spokenlanguagescontainer);
        SpannableString content = new SpannableString("Overview");
       content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        overview.setText(content);
        SpannableString underlinegenre = new SpannableString("genre");
        underlinegenre.setSpan(new UnderlineSpan(), 0, underlinegenre.length(), 0);
        genre.setText(underlinegenre);
        SpannableString underlineproductioncompany = new SpannableString("production company");
        underlineproductioncompany.setSpan(new UnderlineSpan(), 0, underlineproductioncompany.length(), 0);
        procompanies.setText(underlineproductioncompany);
        SpannableString underlinereleasedate = new SpannableString("Release date");
        underlinereleasedate.setSpan(new UnderlineSpan(), 0, underlinereleasedate.length(), 0);
        releasedate.setText(underlinereleasedate);
        SpannableString underlinespokenlang = new SpannableString("Spoken language");
        underlinespokenlang.setSpan(new UnderlineSpan(), 0, underlinespokenlang.length(), 0);
        spokenlang.setText(underlinespokenlang);





       Intent intent=getIntent();
        if(intent!=null){
            url=intent.getStringExtra("url");

            if(url!=null){
        JsonObjectRequest detailsRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(moviedetails.this, "there is data", Toast.LENGTH_SHORT).show();
                try {
                    String backdropLink="https://image.tmdb.org/t/p/original/"+response.getString("backdrop_path");
                    Picasso.get().load(backdropLink).into(backdrop);
                    title.setText(response.getString("original_title"));
                    overviewdesc.setText(response.getString("overview"));
                    //genre
                    JSONArray genres=response.getJSONArray("genres");
                    for(int x=0;x<genres.length();x++){
             //populate genres in the layout
                        JSONObject obj=genres.getJSONObject(x);
                        TextView textView=new TextView(moviedetails.this);
                        textView.setText(String.valueOf(x+1)+". "+obj.getString("name"));
                        textView.setPadding(0,5,0,5);
                        genrecontainer.addView(textView);
                    }
                    //production companies
                    JSONArray obj2=response.getJSONArray("production_companies");
                    for(int y=0;y<obj2.length();y++) {

                    JSONObject procomp=obj2.getJSONObject(y);
                        LinearLayout layout = new LinearLayout(moviedetails.this);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        ImageView logo = new ImageView(moviedetails.this);
                        logo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        logo.setMaxHeight(80);
                        logo.setAdjustViewBounds(true);

                        logo.setMaxWidth(150);

                        TextView company = new TextView(moviedetails.this);

                        Picasso.get().load("https://image.tmdb.org/t/p/original/"+procomp.getString("logo_path")).into(logo);

                        company.setText(procomp.getString("name"));
                        layout.addView(logo);
                        layout.setPadding(0,5,0,5);
                        company.setPadding(0,5,0,5);
                        logo.setPadding(0,5,5,5);
                        layout.addView(company);
                        procompaniesecontainer.addView(layout);
                    }
                    //release date
                    date.setText(response.getString("release_date"));
                    JSONArray obj3=response.getJSONArray("spoken_languages");
                    for(int z=0;z<obj3.length();z++){
                        JSONObject spokenlanguage=obj3.getJSONObject(z);
                        TextView spokenlang=new TextView(moviedetails.this);
                        spokenlang.setText(spokenlanguage.getString("name"));
                        spokenlang.setPadding(0,5,0,5);
                        spokenlangcontainer.addView(spokenlang);
                    }

            } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
        queue= Volley.newRequestQueue(moviedetails.this);
        queue.add(detailsRequest);
            }else{
                Toast.makeText(this, "url is null", Toast.LENGTH_LONG).show();
            }
    }
    }
}