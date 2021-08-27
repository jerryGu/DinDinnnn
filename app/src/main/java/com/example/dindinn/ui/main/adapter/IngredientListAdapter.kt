package com.example.dindinn.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dindinn.entity.IngredientResponse
import dindinn.databinding.ItemIngredientBinding

class IngredientListAdapter(
    private val viewLifecycleOwner: LifecycleOwner
) : ListAdapter<IngredientResponse, IngredientListAdapter.IngredientViewHolder>(
    IngredientDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IngredientViewHolder(ItemIngredientBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position), viewLifecycleOwner)
    }

    class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            ingredient: IngredientResponse,
            viewLifecycleOwner: LifecycleOwner
        ) {

            binding.run {
                lifecycleOwner = viewLifecycleOwner
                title = ingredient.title
                quality = " x" + ingredient.quantity

                executePendingBindings()
            }
        }
    }

}

private object IngredientDiffCallback : DiffUtil.ItemCallback<IngredientResponse>() {
    override fun areItemsTheSame(
        oldItem: IngredientResponse,
        newItem: IngredientResponse
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: IngredientResponse,
        newItem: IngredientResponse
    ): Boolean {
        return oldItem == newItem
    }

}
