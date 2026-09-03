package com.nawa.whatup

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nawa.whatup.databinding.ArticleListItemBinding

class NewsAdapter(private val activity: Activity, private val articles: ArrayList<Articles>) :
    RecyclerView.Adapter<NewsAdapter.NewsVH>() {
    class NewsVH(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsVH {
        return NewsVH(
            ArticleListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NewsVH,
        position: Int
    ) {
        holder.binding.apply {
            articleText.text = articles[position].title
            Glide.with(holder.binding.imgIv.context)
                .load(articles[position].urlToImage)
                .error(R.drawable.broken_image)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(imgIv)

            val url = articles[position].url
            containeCv.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                activity.startActivity(intent)
            }

            shareFab.setOnClickListener {
                ShareCompat.IntentBuilder(activity)
                    .setType("text/plain")
                    .setChooserTitle("Share Article With:")
                    .setText(url)
                    .startChooser()
            }
        }
    }

    override fun getItemCount() = articles.size


}