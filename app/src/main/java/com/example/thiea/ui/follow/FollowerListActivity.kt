package com.example.thiea.ui.follow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thiea.R
import com.example.thiea.data.model.userSearch
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.FollowListService
import retrofit2.Call
import retrofit2.Response

class FollowerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follower_list)
        getFollowerList()
    }
    fun getFollowerList() {
        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val userid = sp.getString("userId", null)
            val service = RetrofitClient.getRetrofitmain()
            val call = service.create(FollowListService::class.java).requestFollowList(userid!!)
            call.enqueue(object : retrofit2.Callback<List<userSearch>> { // 변경된 부분
                override fun onResponse(call: Call<List<userSearch>>, response: Response<List<userSearch>>) { // 변경된 부분
                    if (response.isSuccessful) {
                        val result = response.body()!!
                        val adapter = MyAdapter(result) { uid ->
                            // Handle item click here if needed
                            Log.d("fotest", "언팔ㄱㄱ $uid")
                        }

                        // Assuming you have a RecyclerView with the id recyclerView
                        val recyclerView: RecyclerView = findViewById(R.id.rv_follower)
                        recyclerView.layoutManager = LinearLayoutManager(this@FollowerListActivity)
                        recyclerView.adapter = adapter
                    } else {
                        Log.d("fotest", "팔로워 리스트 실패")
                    }
                }

                override fun onFailure(call: Call<List<userSearch>>, t: Throwable) { // 변경된 부분
                    Log.d("fotest", "팔로워 리스트 실패")
                }
            })
    }

//    fun unFollow(uid:String){
//        val call = RetrofitClient.getRetrofitmain().create(FollowListService::class.java).requestFollowList()
//    }

}