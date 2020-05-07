package com.glob.movies.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.glob.movies.MainMenuRegistry
import com.glob.movies.R
import com.glob.movies.adapters.MovieAdapter
import com.glob.movies.contracts.MainMenuContract
import com.glob.movies.domain.dtos.MovieDto
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_menu.*


private const val INITIAL_PAGE = "INITIAL_PAGE"

class MainMenuFragment : Fragment(), MainMenuContract.View, MovieAdapter.OnItemSelectedListener {

    private var page: Int? = null
    private lateinit var navBottomView: BottomNavigationView
    private lateinit var listener: OnMovieActionListener

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(context = context!!, listener = this)
    }

    private val presenter: MainMenuContract.Presenter by lazy {
        MainMenuRegistry().provide(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            page = it.getInt(INITIAL_PAGE)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMovieActionListener) {
            listener = context
        } else {
            throw ClassCastException(
                "$context must implement MainMenuFragment.OnMovieSelected"
            )
        }
    }

    interface OnMovieActionListener {
        fun onMovieSelected(id: String)
        fun onSectionSelected(title: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main_menu, container, false)
        navBottomView = view.findViewById(R.id.navBottomView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshPage()

        rvContainer.setHasFixedSize(true)
        rvContainer.layoutManager = GridLayoutManager(context, 3)
        rvContainer.adapter = movieAdapter

        //todo show loader
        refreshPage()

        navBottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> presenter
                R.id.navigation_top_rated -> {
                    Toast.makeText(
                        context!!,
                        "Movies Top Rated working on it!",
                        Toast.LENGTH_SHORT
                    ).show()
                    listener.onSectionSelected("Top Rated")
                }
                R.id.navigation_upcoming -> {
                    Toast.makeText(
                        context!!,
                        "Upcoming working on it!",
                        Toast.LENGTH_SHORT
                    ).show()
                    listener.onSectionSelected("Upcoming")
                }
            }
            true
        }
    }

    override fun onMoviesLoaded(movies: List<MovieDto>) {
        movieAdapter.refreshList(movies)
    }

    override fun onMoviesLoadedFail(message: String) {
        Toast.makeText(context!!, " Faild to download Movies", Toast.LENGTH_LONG).show()
    }

    private fun refreshPage() {
        presenter.getMovieList(page ?: 1)
    }

    companion object {
        @JvmStatic
        fun newInstance(atPage: Int) =
            MainMenuFragment().apply {
                arguments = Bundle().apply {
                    putInt(INITIAL_PAGE, atPage)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onItemSelected(id: String) {
        listener.onMovieSelected(id)
    }
}
