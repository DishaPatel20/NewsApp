package com.rku.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// 1st create viewholder then pass it

class NewsListAdapter( private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

// then implement this 3 methods:-
// 1st when u create viewholder this requires at that time
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return  viewHolder
    }
// 2nd it decides how many items u want
    override fun getItemCount(): Int {
        return items.size
    }
// 3rd it binds your items through data
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
    holder.author.text = currentItem.author
    Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun  updateNews(updateNews: ArrayList<News>) {
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
    abstract fun JsonObjectrequest(get: Int, url: String): Any
}

