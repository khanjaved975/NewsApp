package com.javedkhan.newsapp.android.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javedkhan.newsapp.android.R
import kotlinx.android.synthetic.main.item_layout.view.*


class AdapterList(
    val context: Context,
    private val items: List<String>,
    val onClickItem: (Int) -> Unit
) : RecyclerView.Adapter<AdapterList.DataViewHolder>() {
   // class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            onClickItem(position)
        }
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {
            itemView.apply {
                itemView.name.text = item
                /*Glide.with(imageViewAvatar.context)
                    .load(user.avatar)
                    .into(imageViewAvatar)*/
            }
        }
    }
}