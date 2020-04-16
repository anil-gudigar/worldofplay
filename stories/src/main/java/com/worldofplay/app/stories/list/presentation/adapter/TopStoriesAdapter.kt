package com.worldofplay.app.stories.list.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofplay.app.stories.R
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.app.stories.list.presentation.callbacks.ItemListener
import kotlinx.android.synthetic.main.row_item_topstories.view.*

class TopStoriesAdapter(
    var items: ArrayList<StoriesResponse>,
    val context: Context,
    val itemListener: ItemListener
) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_item_topstories, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position], itemListener)
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Holds the TextView that will add each animal to
    private val story = itemView.story
    private val story_url = itemView.story_url

    fun bindData(storiesResponse: StoriesResponse, listener: ItemListener) {
        story.text = storiesResponse.title
        story_url.text = storiesResponse.url
        itemView.setOnClickListener {
            listener.onClicked(storiesResponse)
        }
    }
}