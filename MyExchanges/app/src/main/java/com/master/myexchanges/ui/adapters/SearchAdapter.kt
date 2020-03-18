package com.master.myexchanges.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.master.myexchanges.R
import com.master.myexchanges.domain.Search
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter(private val items: List<Search>) : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item: Search = items[position]
        holder.bind(item)
    }

    class SearchHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.history_list_item, parent, false)) {
        private var tvDate: TextView? = null
        private var tvSource: TextView? = null
        private var tvDestination: TextView? = null

        init {
            tvDate = itemView.findViewById(R.id.tvDate)
            tvSource = itemView.findViewById(R.id.tvSource)
            tvDestination = itemView.findViewById(R.id.tvDestination)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Search) {
            tvDate?.text = item.date?.let {
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(it)
            } ?: "-"
            tvSource?.text = "${String.format("%.2f",item.sourceValue)} - ${item.sourceCurrency}"
            tvDestination?.text = "${String.format("%.2f",item.destinationValue)} - ${item.destinationCurrency}"
        }
    }
}