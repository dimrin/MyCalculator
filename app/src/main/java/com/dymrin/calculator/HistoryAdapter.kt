package com.dymrin.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dymrin.calculator.databinding.ItemHistoryRowBinding

class HistoryAdapter(
    private val results: ArrayList<String>,
    private val clickListener: (String) -> Unit
) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemHistoryRowBinding, clickAtThePosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private val llHistoryItem = binding.llHistoryItem
        val tvResult = binding.tvResult

        init {
            llHistoryItem.setOnClickListener {
                clickAtThePosition(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) { position ->
            clickListener(results[position])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result: String = results[position]
        holder.tvResult.text = result
    }

    override fun getItemCount(): Int {
        return results.size
    }
}