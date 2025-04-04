package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.interfaces.OnItemClickListener;
import com.example.movieapp.models.movieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder>{

ArrayList<movieModel> movies;
OnItemClickListener mylistener;

Context context;

    public movieAdapter(ArrayList<movieModel> movies, Context context,OnItemClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.mylistener=listener;

    }

    @NonNull
    @Override
    public movieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.movieitem,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull movieAdapter.ViewHolder holder, int position) {
        holder.releaseDate.setText(movies.get(position).getReleaseDate());
        holder.title.setText(movies.get(position).getTitle());
        Picasso.get().load(movies.get(position).getBackdropImg()).into(holder.backdrop);
        holder.rating.setRating(Float.valueOf(movies.get(position).getRating()).floatValue());
holder.bind(mylistener,position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView backdrop;
TextView title;
TextView releaseDate;
RatingBar rating;
View itemview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemview=itemView;
            backdrop=itemView.findViewById(R.id.backdrop);
            rating=itemView.findViewById(R.id.ratingbar);
            title=itemView.findViewById(R.id.title);
            releaseDate=itemView.findViewById(R.id.releasedate);

        }
        public void bind(OnItemClickListener listener,int position){
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRecyclerviewItemClick(position,v);
                }
            });

        }


    }
}