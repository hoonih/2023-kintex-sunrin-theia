package com.example.thiea.ui.search_user

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.thiea.BuildConfig
import com.example.thiea.R
import com.example.thiea.data.model.userSearch

class RecyclerViewAdapter(private val context: Context, private val itemList: List<userSearch>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // ViewHolder 클래스 정의
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.user_name)
        val userImage : ImageView = view.findViewById(R.id.ic_profile)
    }

    // onCreateViewHolder: ViewHolder를 생성하고 레이아웃을 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_user_item, parent, false)
        return ViewHolder(view)
    }

    // onBindViewHolder: ViewHolder에 데이터를 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        //이름 설정
        holder.username.text = item.name
        //glide를 이용한 프로필사진 불러오기
        val glideUrl = GlideUrl(BuildConfig.Base_URL+item.profile_picture_url)

        Glide.with(holder.itemView.context)
            .load(glideUrl)
            .override(200, 200)
            .transform(CenterCrop(), CircleCrop())
            .into(holder.userImage)

        holder.itemView.setOnClickListener {
            var intnet = Intent(context, SearchResultActivity::class.java)
            intnet.putExtra("name", item.name)
            intnet.putExtra("uid", item.uid)
            intnet.putExtra("profileurl", item.profile_picture_url)

            context.startActivity(intnet)
        }
    }

    // getItemCount: 데이터 아이템의 총 개수 반환
    override fun getItemCount(): Int {
        return itemList.size
    }
}