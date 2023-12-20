package com.example.thiea.ui.follow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.thiea.BuildConfig
import com.example.thiea.R
import com.example.thiea.data.model.userSearch

class MyAdapter(
    private val dataList: List<userSearch>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val itemView: View = inflater.inflate(R.layout.follow_list_item, parent, false)

        // Return a new holder instance
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val data: userSearch = dataList[position]

        // Set item views based on your views and data model
        holder.itemView.findViewById<TextView>(R.id.user_name).text = data.name

        val glideUrl = GlideUrl(BuildConfig.Base_URL+data.profile_picture_url)

        Glide.with(holder.itemView.context)
            .load(glideUrl)
            .override(200, 200)
            .transform(CenterCrop(), CircleCrop())
            .into(holder.itemView.findViewById(R.id.ic_profile))

        // Set click listener
        holder.itemView.findViewById<Button>(R.id.btn_delete).setOnClickListener {
            onItemClick(data.uid)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.user_name)
        val imageView: ImageView = itemView.findViewById(R.id.ic_profile)
        val del_btn: Button = itemView.findViewById(R.id.btn_delete)
    }
}
