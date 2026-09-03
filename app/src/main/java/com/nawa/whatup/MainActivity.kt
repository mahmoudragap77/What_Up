package com.nawa.whatup

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.nawa.whatup.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadNews()

        binding.refresh.setOnRefreshListener { loadNews() }

    }

    private fun showNews(articles: ArrayList<Articles>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
    }

    private fun loadNews() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val c = retrofit.create(NewsCallable::class.java)

        c.getNews().enqueue(object : Callback<News> {
            override fun onResponse(
                call: Call<News?>,
                response: Response<News?>
            ) {

                val news = response.body()
                val article = news?.articles!!
//                Log.d("trace", " Article $article ")
                article.removeAll{it.title == "[Removed]"}
                showNews(article)

                binding.progress.isVisible = false
                binding.refresh.isRefreshing = false

            }

            override fun onFailure(
                call: Call<News?>,
                t: Throwable
            ) {
//                Log.d("body" , t.message.toString())
                binding.progress.isVisible = false
            }

        })
    }
}