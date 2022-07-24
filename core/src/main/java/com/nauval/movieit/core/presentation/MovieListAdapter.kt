package com.nauval.movieit.core.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nauval.movieit.core.R
import com.nauval.movieit.core.databinding.MovieItemBinding
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.util.Utils

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ListMovieViewHolder>() {

    private val data = ArrayList<Movie>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieViewHolder =
        ListMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )

    override fun onBindViewHolder(holder: ListMovieViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Movie>?) {
        if (newData == null) return
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemViewType(position: Int): Int = position

    interface OnItemClickCallback {
        fun onItemClicked(item: Movie, position: Int)
    }

    inner class ListMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MovieItemBinding.bind(itemView)
        private val context = itemView.context

        fun bind(data: Movie, position: Int) {
            with(data) {
                binding.itemTitle.text = title
                binding.itemRating.text = context.getString(R.string.movie_rating, rating)

                Glide.with(context)
                    .load(getActualPosterUrl())
                    .placeholder(Utils.getCircularProgressDrawable(context))
                    .error(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.poster_placeholder
                        )
                    )
                    .into(binding.itemImage)

                binding.root.setOnClickListener {
                    onItemClickCallback.onItemClicked(
                        data,
                        position
                    )
                }
            }
        }
    }
}