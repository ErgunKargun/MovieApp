package com.example.movieapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieListRowBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.view.MovieListFragmentDirections
import com.squareup.picasso.Picasso

class MovieListAdapter(val movieList : List<Movie>, val listener : Listener) : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    interface Listener {
        fun onItemClick(movie: Movie)
    }

    class MovieHolder (val binding : MovieListRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val movie = movieList[position]

        Picasso.get().load(movie.Poster).into(holder.binding.movieRowImageView)
        holder.binding.movieTitleRowTextView.text = movie.Title
        holder.binding.movieYearRowTextView.text = movie.Year

        holder.itemView.setOnClickListener {
            listener.onItemClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}