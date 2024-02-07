package com.example.dailynews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class NewsAdapter(private val listener: NewsItemClicked) : RecyclerView.Adapter<NewsViewholder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewholder {     // its returns the view holder
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)      // It convert the xml code to the view holder
        val viewholder = NewsViewholder(view)
        view.setOnClickListener{            // It was an interface which is used to handle onclick function
            listener.onItemClicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }


    override fun onBindViewHolder(holder: NewsViewholder, position: Int) {      //  It will bind the data with the corresponding item
        val currentItem = items[position]        // find the current Position of item
//        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        holder.title.text = currentItem.title
//        holder.author.text =currentItem.description
        Glide.with(holder.itemView.context).load(currentItem.image_url).into(holder.imageView)
    }

    override fun getItemCount(): Int {              // call only once
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updatedNews : ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}
class NewsViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val title : TextView = itemView.findViewById(R.id.title)
    val imageView : ImageView = itemView.findViewById(R.id.imageView)
}

interface NewsItemClicked{
    fun onItemClicked(item : News)
}