package com.tuan.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.character_item.view.*
import com.tuan.rickandmorty.R
import com.tuan.rickandmorty.di.GlideApp
import com.tuan.rickandmorty.model.Character

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    companion object {
        private const val VIEW_ITEM = 0
        private const val VIEW_LOADING = 1
        private const val VIEW_ERROR = 2
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var dataset = emptyList<Character>()
    private var loading = false
    private var error: Throwable? = null

    fun setDataset(newDataset: List<Character>) {
        dataset = newDataset
        notifyDataSetChanged()
    }

    fun setLoading(newLoading: Boolean) {
        loading = newLoading
        notifyDataSetChanged()
    }

    fun setError(newError: Throwable?) {
        error = newError
        notifyDataSetChanged()
    }

    private var onItemClick: ((Character) -> Unit)? = null

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClick = listener
    }

    fun clearOnItemClickListener() {
        onItemClick = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            when (viewType) {
                VIEW_ITEM -> R.layout.character_item
                VIEW_LOADING -> R.layout.character_item_loading
                VIEW_ERROR -> R.layout.character_item_error
                else -> throw IllegalStateException()
            }, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) != VIEW_ITEM) {
            return
        }

        val character = dataset[position]

        GlideApp
            .with(holder.itemView.context)
            .load(character.image)
            .centerCrop()
            .transition(withCrossFade())
            .into(holder.itemView.character_image)

        holder.itemView.character_name.text = character.name
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(character)
        }
    }

    override fun getItemCount(): Int {
        var count = dataset.size
        if (loading || error != null) {
            count += 1
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == itemCount - 1 && loading -> VIEW_LOADING
            position == itemCount - 1 && error != null -> VIEW_ERROR
            else -> VIEW_ITEM
        }
    }

    fun getSpanSizeLookup() = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (position == itemCount - 1 && (loading || error != null)) {
                2
            } else {
                1
            }
        }
    }
}