package com.mvaresedev.swapp.ui.character_items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvaresedev.swapp.R
import com.mvaresedev.swapp.databinding.HolderCharacterListBinding
import com.mvaresedev.swapp.databinding.HolderCharacterGridBinding
import com.mvaresedev.swapp.domain.models.Character

class CharacterPagingAdapter(private val onItemClick: (Character) -> Unit, private val viewStyle: ViewStyle) : PagingDataAdapter<Character, CharacterPagingAdapter.CharacterVH>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewSype: Int): CharacterVH {
        return if(this.viewStyle == ViewStyle.LIST) {
            val binding = HolderCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ListCharacterVH(binding)
        } else {
            val binding = HolderCharacterGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GridCharacterVH(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount){
            LOADING_VIEW_TYPE
        }else {
            super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(holder: CharacterVH, position: Int) {
        getItem(position)?.let { holder.bindData(it, onItemClick) }
    }

    abstract class CharacterVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bindData(character: Character, onItemClick: (Character) -> Unit)
    }

    class ListCharacterVH(private val binding: HolderCharacterListBinding): CharacterVH(binding.root) {

        override fun bindData(character: Character, onItemClick: (Character) -> Unit) {
            with(character) {
                binding.nameTxt.text = name
                Glide.with(binding.root).load(avatarUrl).placeholder(R.drawable.ic_placeholder).centerCrop().into(binding.avatarImg)
            }
            itemView.setOnClickListener { onItemClick(character) }
        }
    }

    class GridCharacterVH(private val binding: HolderCharacterGridBinding): CharacterVH(binding.root) {

        override fun bindData(character: Character, onItemClick: (Character) -> Unit) {
            with(character) {
                binding.nameTxt.text = name
                Glide.with(binding.root).load(avatarUrl).placeholder(R.drawable.ic_placeholder).centerCrop().into(binding.avatarImg)
            }
            itemView.setOnClickListener { onItemClick(character) }
        }
    }


    class DiffUtilCallback: DiffUtil.ItemCallback<Character>() {

        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }

    companion object {
        const val LOADING_VIEW_TYPE = 5
    }

}