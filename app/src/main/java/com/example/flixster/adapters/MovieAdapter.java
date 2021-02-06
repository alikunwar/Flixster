package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //inflates a layout from item_movie.xml and return inside ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View movieView =  LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);

        return new ViewHolder(movieView);
    }

    //populates data into the view through viewholder
    //take data at that position and put it into view contain inside viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Get the movie at the passed in position

        Movie movie = movies.get(position);
        //Bind the movie data into the viewHolder

        holder.bind(movie);

    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //define every elements of the view
        //getting reference of each of the component of the view
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            //defining textView and imageView source i.e viewHolder
            tvTitle = itemView.findViewById((R.id.player));
            tvOverview = itemView.findViewById((R.id.tvOverview));
            ivPoster = itemView.findViewById((R.id.ivPoster));
            container = itemView.findViewById(R.id.container);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText((movie.getOverview()));
            String imageUrl;

            // if landscape mode is on use imageUrl as backdropPath
            //else use imageUrl as PosterPath

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getBackdropPath();
            }

            else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);

            //register clickListener on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //navigate to a new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title",movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });
        }
    }
}
