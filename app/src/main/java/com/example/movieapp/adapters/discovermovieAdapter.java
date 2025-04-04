package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.interfaces.OnItemClickListener;
import com.example.movieapp.models.discovermoviemodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class discovermovieAdapter extends RecyclerView.Adapter<discovermovieAdapter.ViewHolder>{
    Context context;
    ArrayList<discovermoviemodel> movies;
    OnItemClickListener mylistener;

    public discovermovieAdapter(Context context, ArrayList<discovermoviemodel> movies,OnItemClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.mylistener=listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.discovermoreitem,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        Picasso.get().load(movies.get(position).getBackdropImage()).into(holder.movieBackdrop);
        holder.releaseDate.setText("Release date: "+movies.get(position).getDate());
        holder.movieOverView.setText(movies.get(position).getOverview());
        holder.movieRatingBar.setRating(Float.valueOf(movies.get(position).getRating()).floatValue());
        holder.bind(mylistener,position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      TextView movieTitle;
      ImageView movieBackdrop;
      TextView releaseDate;
      TextView movieOverView;
      RatingBar movieRatingBar;
View itemView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            this.itemView=itemView;
            movieTitle=itemView.findViewById(R.id.discoverMovieTitle);
            movieBackdrop=itemView.findViewById(R.id.discoverMovieBackdropimage);
            releaseDate=itemView.findViewById(R.id.discoverMovieDate);
            movieOverView=itemView.findViewById(R.id.discoverMovieOverview);
            movieRatingBar=itemView.findViewById(R.id.discoverMovieRatingBar);
        }
        public void bind(OnItemClickListener listener,int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.onRecyclerviewItemClick(position,v);
                }
            });
        }
    }
}
