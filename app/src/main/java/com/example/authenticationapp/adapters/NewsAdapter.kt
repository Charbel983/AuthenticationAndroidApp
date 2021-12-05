package com.example.authenticationapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.authenticationapp.R
import com.example.authenticationapp.parsedData.Result
import com.squareup.picasso.Picasso

class NewsAdapter (private val newsList : List<Result>?, private val context : Context) : RecyclerView.Adapter<NewsAdapter.NewsHolder>(){

    class NewsHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val uri : ImageView = itemView.findViewById(R.id.newsImageView)
        val title : TextView = itemView.findViewById(R.id.newsTitleTextView)
        val description : TextView = itemView.findViewById(R.id.newsDescriptionTextView)
        val date : TextView = itemView.findViewById(R.id.newsDateTextView)
        val cardView : CardView = itemView.findViewById(R.id.newsCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_holder, parent, false)
        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = newsList?.get(position)
        holder.title.text = news?.title
        holder.description.text = news?.abstract
        holder.date.text = news?.publishedDate
        if(news != null){
            try{
                val image = news.media[0].mediaMetadata[0].url
                Picasso.with(context).load(image).into(holder.uri)
            }catch(e : IndexOutOfBoundsException){}
        }
        holder.cardView.setOnClickListener {
            val intent = Intent("android.intent.action.VIEW", Uri.parse(news?.url))
            startActivity(context,intent, null)
        }
    }

    override fun getItemCount(): Int {
        if (newsList != null) {
            return newsList.size
        }
        return 0
    }
}