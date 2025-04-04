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
import com.example.movieapp.models.tvshowsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class tvshowsAdapter extends RecyclerView.Adapter<tvshowsAdapter.ViewHolder> {
    Context context;
    OnItemClickListener mylistener;
    ArrayList<tvshowsModel> tvshows=new ArrayList<>();

    public tvshowsAdapter(Context context, OnItemClickListener mylistener, ArrayList<tvshowsModel> tvshows) {
        this.context = context;
        this.mylistener = mylistener;
        this.tvshows = tvshows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View viewitem=inflater.inflate(R.layout.tvshowsitem,parent,false);
        return new ViewHolder(viewitem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(tvshows.get(position).getTvshowBackdrop()).into(holder.tvbackdrop);
        holder.tvname.setText(tvshows.get(position).getTvshowname());
        holder.tvoverview.setText(tvshows.get(position).getTvshowOverview());
        holder.tvrating.setRating(Float.valueOf(tvshows.get(position).getRating()).floatValue());
        holder.bind(position);


    }

    @Override
    public int getItemCount() {
        return tvshows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView tvbackdrop;
TextView tvname;
TextView tvoverview;
RatingBar tvrating;

View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            tvbackdrop=itemView.findViewById(R.id.tvbackdrop);
            tvname=itemView.findViewById(R.id.tvshowname);
            tvoverview=itemView.findViewById(R.id.tvshowoverview);
            tvrating=itemView.findViewById(R.id.tvshowratingbar);

        }
        public void bind(int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mylistener.onRecyclerviewItemClick(position,v);
                }
            });
        }
    }
}
