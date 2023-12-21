package com.example.thiea.ui.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.thiea.BuildConfig
import com.example.thiea.R
import com.example.thiea.data.model.is_following
import com.example.thiea.data.model.userSearch
import com.example.thiea.databinding.ActivityMyPageBinding
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.SearchUserService
import com.example.thiea.ui.follow.FollowerListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyPageActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<TextView>(R.id.tx_follower).setOnClickListener{var intent = Intent(this, FollowerListActivity::class.java)
            startActivity(intent)}
        findViewById<TextView>(R.id.tx_follower).setOnClickListener{var intent = Intent(this, FollowerListActivity::class.java)
            startActivity(intent)}

        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE);
        val userid = sp.getString("userId", null)


        val searchservie = RetrofitClient.getRetrofitmain().create(SearchUserService::class.java)

        searchservie.usersearch(userid.toString()).enqueue(object :
            Callback<userSearch> {
            override fun onResponse(call: Call<userSearch>, response: Response<userSearch>) {
                if (response.isSuccessful) {
                    val myResponse = response.body()
                    binding.txName.text = myResponse?.name.toString()

                    val glideUrl = GlideUrl(BuildConfig.Base_URL+ myResponse!!.profile_picture_url)

                    Glide.with(this@MyPageActivity)
                        .load(glideUrl)
                        .override(200, 200)
                        .transform(CenterCrop(), CircleCrop())
                        .into(binding.igProfile)
                } else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("theia", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<userSearch>, t: Throwable) {
                Log.d("theia", "API FAIL: ${call}")
            }

        })

    }
}