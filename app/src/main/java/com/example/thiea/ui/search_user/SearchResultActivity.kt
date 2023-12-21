package com.example.thiea.ui.search_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RestrictTo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.thiea.BuildConfig
import com.example.thiea.R
import com.example.thiea.data.model.Post
import com.example.thiea.data.model.User
import com.example.thiea.data.model.is_following
import com.example.thiea.data.model.recent
import com.example.thiea.databinding.ActivitySearchBinding
import com.example.thiea.databinding.ActivitySearchResultBinding
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.FollowListService
import com.example.thiea.remote.service.PingService
import com.example.thiea.remote.service.SearchUserService
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

        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE);                             //팔로우 여부 확인
        val userid = sp.getString("userId", null)

        val searchservie = RetrofitClient.getRetrofitmain().create(SearchUserService::class.java)

        searchservie.isfollowing(userid.toString(), intent.getStringExtra("name").toString()).enqueue(object : Callback<is_following> {
            override fun onResponse(call: Call<is_following>, response: Response<is_following>) {
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

            override fun onFailure(call: Call<is_following>, t: Throwable) {
                Log.d("theia", "API FAIL: ${call}")
            }

        })




    }
}