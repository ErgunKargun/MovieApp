package com.example.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.MovieListAdapter
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.MovieViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class MovieListFragment : Fragment(), MovieListAdapter.Listener, AndroidScopeComponent {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieList : List<Movie>

    private lateinit var searchText : String

    private val viewModel by viewModel<MovieViewModel>()

    override val scope: Scope by fragmentScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            searchText = binding.searchMovieEditText.text.toString()
            viewModel.getMoviesFromAPI(searchText)
        }

        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(movie: Movie) {
        Toast.makeText(requireContext(),"Clicked on: ${movie.Title}",Toast.LENGTH_SHORT).show()
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie.imdbID!!)
        findNavController(this).navigate(action)
    }

    fun observeLiveData() {

        viewModel.movies.observe(viewLifecycleOwner, Observer { movieList ->
            movieList?.let {
                binding.movieListRecyclerView.visibility = View.VISIBLE
                binding.movieListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                val adapter = MovieListAdapter(ArrayList(movieList.data ?: arrayListOf()),this@MovieListFragment)
                binding.movieListRecyclerView.adapter = adapter
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it.data == true) {
                    binding.errorTextView.visibility = View.VISIBLE
                } else {
                    binding.errorTextView.visibility = View.GONE
                }
            }
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer { progress->
            progress?.let {
                if (it.data == true) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.movieListRecyclerView.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }
}