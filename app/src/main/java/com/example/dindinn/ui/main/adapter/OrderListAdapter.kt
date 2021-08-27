package com.example.dindinn.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dindinn.model.Order
import com.example.dindinn.ui.main.vm.MainViewModel
import dindinn.databinding.ItemOrderBinding

class OrderListAdapter(
    private val viewModel: MainViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : ListAdapter<Order, OrderListAdapter.OrderViewHolder>(OrderDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(ItemOrderBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position), viewLifecycleOwner)
    }

    class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var order: Order
        fun bind(viewModel: MainViewModel, order: Order, viewLifecycleOwner: LifecycleOwner) {
            this.order = order
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                vm = viewModel
                orderId = order.id
                orderItems = order.addon.joinToString("\n") {
                    it.title + " x" + it.quantity
                }
                orderTime = order.expiredString
                orderFooter = "${
                    order.addon.map {
                        it.quantity
                    }.sum()
                } items"
                expired = order.expired

                executePendingBindings()
            }
            order.startTimer(binding.root.context)
        }
    }

}

private object OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(
        oldItem: Order,
        newItem: Order
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Order,
        newItem: Order
    ): Boolean {
        return oldItem.id == newItem.id
    }

}
