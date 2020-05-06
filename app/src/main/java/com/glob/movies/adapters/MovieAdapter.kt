package com.glob.movies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glob.movies.R
import com.glob.movies.domain.dtos.MovieDto

class MovieAdapter(
    private var movieList: ArrayList<MovieDto> = arrayListOf(),
    private val context: Context,
    private val listener: OnItemSelectedListener
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moview = movieList[position]
        holder.title.text = moview.title
        holder.item.setOnClickListener {
            listener.onItemSelected(moview.id)
        }
        Glide.with(context)
            .load(moview.frontImage)
            .into(holder.poster)
    }

    fun refreshList(newMovies: List<MovieDto>) {
        newMovies.forEach {
            movieList.add(it)
        }
        notifyDataSetChanged()
    }

    interface OnItemSelectedListener {
        fun onItemSelected(id: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView
        val title: TextView
        val item: ConstraintLayout

        init {
            poster = itemView.findViewById(R.id.ivPoster)
            title = itemView.findViewById(R.id.tvTitle)
            item = itemView.findViewById(R.id.clItem)
        }
    }
}