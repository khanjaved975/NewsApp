package com.javedkhan.newsapp.android.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javedkhan.newsapp.android.R
import com.javedkhan.newsapp.android.models.Result
import kotlinx.android.synthetic.main.item_layout.view.*


class AdapterList(
    val context: Context,
    private var items: List<Result>,
    val onClickItem: (Result) -> Unit
) : RecyclerView.Adapter<AdapterList.DataViewHolder>() {

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
            onClickItem(items[position])
        }
        //To add itemlist animation
       // ItemAnimation.animateBottomUp(holder.itemView,position)
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Result) {
            itemView.apply {
                itemView.txt_title.text = item.title
                itemView.txt_description.text = item.adxKeywords
                itemView.txt_date.text = item.publishedDate
                itemView.txt_author.text = item.byline

            }
        }
    }
}