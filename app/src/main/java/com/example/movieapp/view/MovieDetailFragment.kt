package com.example.movieapp.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieListAdapter
import com.example.movieapp.databinding.FragmentMovieDetailBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.MovieViewModel
import com.squareup.picasso.Picasso
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import java.net.URI

class MovieDetailFragment : Fragment(), AndroidScopeComponent {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var imdbId : String

    private val viewModel by viewModel<MovieViewModel>()

    override val scope: Scope by fragmentScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imdbId = MovieDetailFragmentArgs.fromBundle(it).imdbId.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imdbId?.let {
            viewModel.getMovieDetailFromAPI(it)
            observeLiveData()
        }
    }

    private fun observeLiveData() {
        viewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                it.data?.let { movieData ->
                    bindMovie(movieData)
                }
            }
        })
    }

    private fun bindMovie(movie : Movie) {
        Picasso.get().load(movie.Poster).into(binding.movieDetailImageView)
        binding.movieDetailActorsTextView.text = "Actors: ${movie.Actors}"
        binding.movieDetailCountryTextView.text = "Country: ${movie.Country}"
        binding.movieDetailTitleTextView.text = "Title: ${movie.Title}"
        binding.movieDetailYearTextView.text = "Year: ${movie.Year}"
        binding.movieDetailDirectorTextView.text = "Director: ${movie.Director}"
        binding.movieDetailIMDBRatingTextView.text = "IMDB Rating: ${movie.imdbRating}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}