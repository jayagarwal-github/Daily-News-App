package com.example.dailynews

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.dailynews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var Adapter: NewsAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerview: RecyclerView = findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        fetchData()
       Adapter = NewsAdapter(this)
       binding.recyclerview.adapter =Adapter       // when we put m before any var then it is mean that it is accessible the multiple classes

    }

    private fun fetchData(){
        val url ="https://newsdata.io/api/1/news?apikey=pub_37452b2a987f6e41822bf3b14973c0118af53&category=technology&country=in&language=en"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                 val newsJsonArray = it.getJSONArray("results")
                 val newsArray = ArrayList<News>()
                 for (i in 0 until newsJsonArray.length()){
                     val newsJsonObject = newsJsonArray.getJSONObject(i)
                     val news = News(
                         newsJsonObject.getString("title"),
                         newsJsonObject.getString("link"),
                         newsJsonObject.getString("image_url"),

                     )
                     newsArray.add(news)

                 }
                 Adapter.updateNews(newsArray)
             }
        ) {

        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.link))
    }
}
