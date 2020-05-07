package com.glob.movies.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.glob.movies.DetailsRegistry
import com.glob.movies.R
import com.glob.movies.contracts.DetailsContract
import com.glob.movies.domain.dtos.MovieDto
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.tvTitle


private const val ID_MOVIE = "ID_MOVIE"

class DetailFragment : Fragment(), DetailsContract.View, View.OnClickListener{
    private var idMovie: String? = null
    private lateinit var listener: OnMovieDetailListener

    private val presenter: DetailsContract.Presenter by lazy {
        DetailsRegistry().provide(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idMovie = it.getString(ID_MOVIE)
        }
        //onCreateOptionsMenu()
        //setSupportActionBar(toolbarAction)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        val actionBar: ActionBar = (activity as MainActivity).getSupportActionBar()!!
        actionBar.setTitle("First Fragment")
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            android.R.id.home -> {
                listener.goHomeBtn()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMovieDetailListener) {
            listener = context
        } else {
            throw ClassCastException(
                "$context must implement MainMenuFragment.OnMovieSelected"
            )
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.getMovie("$idMovie")
    }

    interface OnMovieDetailListener {
        fun onMovieSd(id: String)
        fun onSectionSd(title: String)
        fun goHomeBtn()
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
        showMovieDetial(movieDto)
    }

    override fun onMovieLoadedFail(message: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showMovieDetial(movieDto: MovieDto) {
        lblOverview.visibility = View.VISIBLE
        tvTitle.text = movieDto.title
        tvOverview.text = movieDto.overView
        getImage(movieDto.frontImage, ivBackground)
        getImage(movieDto.frontImage, ivPoster)
    }

    private fun getImage(path: String, place: ImageView) {
        Glide.with(context!!)
            .load("https://image.tmdb.org/t/p/w500/${path}")
            .into(place)
    }
}
