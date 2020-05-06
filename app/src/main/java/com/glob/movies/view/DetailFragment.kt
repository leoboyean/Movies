package com.glob.movies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glob.movies.DetailsRegistry

import com.glob.movies.R
import com.glob.movies.contracts.DetailsContract
import com.glob.movies.domain.dtos.MovieDto

private const val ID_MOVIE = "ID_MOVIE"

class DetailFragment : Fragment() , DetailsContract.View {
    private var param1: String? = null

    private val presenter: DetailsContract.Presenter by lazy {
        DetailsRegistry().provide(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ID_MOVIE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(idMovie: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_MOVIE, idMovie)
                }
            }
    }

    override fun onMovieLoaded(movieDto: MovieDto) {

    }

    override fun onMovieLoadedFail(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
