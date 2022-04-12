package com.javedkhan.currencyapp.android.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.javedkhan.currencyapp.android.R
import kotlinx.android.synthetic.main.item_layout.view.*


class AdapterCurrencyList(
    val context: Context,
    val items: List<String>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val items = items[position]

        holder.itemView.name.text = items

    }
}