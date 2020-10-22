package com.rku.newsapp

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter( this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=4345a673fd0642a3b10245b34759a1d3"
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener {
                    val newsJsonArray = it.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJSONObject =newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJSONObject.getString("title"),
                                newsJSONObject.getString("author"),
                                newsJSONObject.getString("url"),
                                newsJSONObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    mAdapter.updateNews(newsArray)

                },
                Response.ErrorListener {

                })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {

        val builder =  CustomTabsIntent.Builder();
        val  customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));

    }

    override fun JsonObjectrequest(get: Int, url: String): Any {
        TODO("Not yet implemented")
    }

}