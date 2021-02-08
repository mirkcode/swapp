package com.mvaresedev.swapp.ui.character_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.swapp.databinding.HolderFilmBinding
import com.mvaresedev.swapp.domain.models.Film

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmVH>() {

    private val films = ArrayList<Film>()

    fun submitData(films: List<Film>) {
        this.films.clear()
        this.films.addAll(films)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH {
        val binding = HolderFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmVH(binding)
    }

    override fun onBindViewHolder(holder: FilmVH, position: Int) {
        holder.bindData(films[position])
    }

    override fun getItemCount() = films.size

    class FilmVH(private val binding: HolderFilmBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(film: Film) {
            binding.titleTxt.text = film.title
            binding.yearTxt.text = film.year
            binding.openingCrawlTxt.text = film.openingCrawl
        }

    }
}