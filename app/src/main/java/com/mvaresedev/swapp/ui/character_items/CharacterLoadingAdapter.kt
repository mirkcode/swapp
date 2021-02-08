package com.mvaresedev.swapp.ui.character_items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.swapp.databinding.HolderCharacterLoadingBinding

class CharacterLoadingAdapter (private val retry: () -> Unit) : LoadStateAdapter<CharacterLoadingAdapter.CharacterLoadingVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CharacterLoadingVH {
        val binding = HolderCharacterLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterLoadingVH(binding, retry)
    }

    override fun onBindViewHolder(holder: CharacterLoadingVH, loadState: LoadState) {
        holder.bindState(loadState)
    }

    class CharacterLoadingVH(
        private val binding: HolderCharacterLoadingBinding,
        retry: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bindState(loadState: LoadState) {
            binding.animationView.isVisible = loadState != LoadState.Loading
            binding.errorTxt.isVisible = loadState != LoadState.Loading
            binding.retryBtn.isVisible = loadState != LoadState.Loading
            binding.loadingPb.isVisible = loadState == LoadState.Loading
        }
    }

}