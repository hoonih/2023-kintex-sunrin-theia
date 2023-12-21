package com.example.thiea.ui.follow

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.thiea.BuildConfig
import com.example.thiea.R
import com.example.thiea.data.model.FollowerInfo
import com.example.thiea.data.model.FollowingInfo
import com.example.thiea.ui.search_user.SearchResultActivity

class Follower_MyAdapter(
    private val context: Context,
    private val dataList: FollowerInfo, // Nullable list
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<Follower_MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
            val itemView: View = inflater.inflate(R.layout.follower_list_item, parent, false)
            return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Check if dataList is not null and position is within bounds
        if (dataList != null && position in dataList.following.indices) {
            // Get the data model
            val data: FollowerInfo = dataList

            // Set item views based on your views and data model
            holder.itemView.findViewById<TextView>(R.id.user_name).text = data.following[position].name

            val glideUrl = GlideUrl(BuildConfig.Base_URL + data.following[position].profile_picture_url)

            Glide.with(holder.itemView.context)
                .load(glideUrl)
                .override(200, 200)
                .transform(CenterCrop(), CircleCrop())
                .into(holder.itemView.findViewById(R.id.ic_profile))

            // Set click listener
            holder.itemView.setOnClickListener {
                var intnet = Intent(context, SearchResultActivity::class.java)
                intnet.putExtra("name", data.following[position].name)
                intnet.putExtra("uid", data.following[position].uid)
                intnet.putExtra("profileurl", data.following[position].profile_picture_url)

                context.startActivity(intnet)
            }
        }
    }

    override fun getItemCount(): Int {
        // Return the size of dataList if not null, otherwise 0
        return dataList.following.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}



