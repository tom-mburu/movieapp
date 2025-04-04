package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.interfaces.OnItemClickListener;
import com.example.movieapp.models.personModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class peopleAdapter extends RecyclerView.Adapter<peopleAdapter.ViewHolder> {
    Context context;
    ArrayList<personModel> persons;
    OnItemClickListener mylistener;

    public peopleAdapter(Context context, ArrayList<personModel> persons, OnItemClickListener mylistener) {
        this.context = context;
        this.persons = persons;
        this.mylistener = mylistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View viewitem=inflater.inflate(R.layout.personitem,parent,false);
        return new ViewHolder(viewitem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(persons.get(position).getProfilepiclink()).into(holder.profilepic);
        holder.profilename.setText(persons.get(position).getProfilename());
        holder.knownfor.setText(persons.get(position).getKnownfor());
       holder.bind(position,mylistener);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
 CircleImageView profilepic;
 TextView profilename;
 TextView knownfor;
 View itemview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemview=itemView;
            profilepic=itemView.findViewById(R.id.profilepic);
            profilename=itemView.findViewById(R.id.profilename);
            knownfor=itemView.findViewById(R.id.knownfor);
        }
        public void bind(int position,OnItemClickListener listener){
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  listener.onRecyclerviewItemClick(position,v);
                }
            });

        }
    }
}
