package com.example.jsonapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
    implements OnMovieItemClickListener{

    OnMovieItemClickListener listener;

    ArrayList<Movie> items=new ArrayList<Movie>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.movie_item,parent,false);

        return new ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie item=items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Movie item){
        items.add(item);
    }

    public void setItems(ArrayList<Movie> items) {
        this.items = items;
    }

    public Movie getItem(int position){
        return items.get(position);
    }

    public void setOnItemClickListener(OnMovieItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener !=null){
            listener.onItemClick(holder,view,position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle,tvCnt;

        public ViewHolder(@NonNull final View itemView, final OnMovieItemClickListener listener) {
            super(itemView);


            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvCnt=itemView.findViewById(R.id.tvCnt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    if(listener !=null){
                        listener.onItemClick(ViewHolder.this,view,position);
                    }
                }
            });
        }
        public void setItem(Movie item){
            tvTitle.setText(item.movieNm);
            tvCnt.setText(item.audiCnt+"명");
        }
    }
}
