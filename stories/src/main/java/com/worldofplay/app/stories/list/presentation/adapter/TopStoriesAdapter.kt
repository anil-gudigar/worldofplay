package com.worldofplay.app.stories.list.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofplay.app.stories.R
import com.worldofplay.app.stories.details.domain.StoriesResponse
import kotlinx.android.synthetic.main.row_item_topstories.view.*

class TopStoriesAdapter(var items : ArrayList<StoriesResponse>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item_topstories, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.story?.text = items.get(position).title
        holder?.story_url?.text = items.get(position).url
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val story = view.story
    val story_url = view.story_url
}