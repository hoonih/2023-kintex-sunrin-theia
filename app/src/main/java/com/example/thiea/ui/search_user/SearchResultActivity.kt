package com.example.thiea.ui.search_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.thiea.BuildConfig
import com.example.thiea.R
import com.example.thiea.data.model.Post
import com.example.thiea.data.model.User
import com.example.thiea.data.model.message
import com.example.thiea.data.model.recent
import com.example.thiea.databinding.ActivitySearchBinding
import com.example.thiea.databinding.ActivitySearchResultBinding
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.FollowListService
import com.example.thiea.remote.service.PingService
import com.example.thiea.ui.main.dialog.CompleteDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txName.text = intent.getStringExtra("name")

        val glideUrl = GlideUrl(BuildConfig.Base_URL+intent.getStringExtra("profileurl"))

        Glide.with(this)
            .load(glideUrl)
            .override(200, 200)
            .transform(CenterCrop(), CircleCrop())
            .into(binding.igProfile)

        val postservice = RetrofitClient.getRetrofitmain().create(FollowListService::class.java)

        postservice.profilelastpost(intent.getStringExtra("uid").toString()).enqueue(object :
            Callback<recent> {
            override fun onResponse(call: Call<recent>, response: Response<recent>) {

                if (response.isSuccessful) {
                    val myResponse = response.body()
                } else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("theia", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<recent>, t: Throwable) {
                Log.d("theia", "API FAIL: ${call}")
            }
        })
        findViewById<LinearLayout>(R.id.bt_following).setOnClickListener{
            val call = RetrofitClient.getRetrofitmain().create(FollowListService::class.java).follow(
                getSharedPreferences("autoLogin", MODE_PRIVATE).getString("userId", null)!!,
                intent.getStringExtra("uid").toString()
            )
            call.enqueue(object : Callback<message> {
                override fun onResponse(
                    call: Call<message>,
                    response: Response<message>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()!!
                        Log.d("theia", "팔로우 성공$result")
                    } else {
                        Log.d("theia", "팔로우 실패"+response.message())
                    }
                }

                override fun onFailure(call: Call<message>, t: Throwable) {
                    Log.d("theia", "팔로우 실패"+t.message)
                }
            })
        }

    }
}