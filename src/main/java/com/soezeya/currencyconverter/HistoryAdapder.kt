package com.soezeya.currencyconverter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soezeya.currencyconverter.databinding.ItemConversionHistoryBinding

class HistoryAdapter : ListAdapter<ConversionHistory, HistoryAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemConversionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ConversionHistory) {
            binding.apply {
                fromAmount.text = item.fromAmount
                toAmount.text = item.toAmount
                timestamp.text = item.timestamp
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConversionHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ConversionHistory>() {
        override fun areItemsTheSame(oldItem: ConversionHistory, newItem: ConversionHistory): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: ConversionHistory, newItem: ConversionHistory): Boolean {
            return oldItem == newItem
        }
    }
}