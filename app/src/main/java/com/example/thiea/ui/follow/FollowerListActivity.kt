package com.example.thiea.ui.follow

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thiea.R
import com.example.thiea.data.model.FollowerInfo
import com.example.thiea.data.model.FollowingInfo
import com.example.thiea.data.model.message
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.FollowListService
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response

class FollowerListActivity : AppCompatActivity() {
    private lateinit var textFollowing: TextView
    private lateinit var textFollower: TextView
    private lateinit var textFriends: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follower_list)

        textFollowing = findViewById(R.id.textFollowing)
        textFollower = findViewById(R.id.textFollower)
        textFriends = findViewById(R.id.textFriends)
        // 초기 선택 (예를 들어, 첫 번째 텍스트뷰를 선택)
        onMenuSelected(textFollowing)

        // 각 텍스트뷰에 클릭 리스너 설정
        textFollowing.setOnClickListener { onMenuSelected(it as TextView) }
        textFollower.setOnClickListener { onMenuSelected(it as TextView) }
        textFriends.setOnClickListener { onMenuSelected(it as TextView) }
        getFollowerList()
        findViewById<TextView>(R.id.user_name).text = getSharedPreferences("autoLogin", MODE_PRIVATE).getString("userName", null)
    }
    fun getFollowingList(){
        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val userid = sp.getString("userId", null)
        val service = RetrofitClient.getRetrofitmain()
        val call = service.create(FollowListService::class.java).requestFollowingList(userid!!)
        call.enqueue(object : retrofit2.Callback<FollowingInfo>{ // 변경된 부분
            override fun onResponse(call: Call<FollowingInfo>, response: Response<FollowingInfo>) { // 변경된 부분
                if (response.isSuccessful) {
                    val result = response.body()!!
                    Log.d("fortest", "팔로잉 리스트 성공$result")
                    val adapter = MyAdapter(result) { uid ->
                        // Handle item click here if needed
                        unFollow(uid)
                        Log.d("fotest", "언팔ㄱㄱ $uid")
                    }
                    // Assuming you have a RecyclerView with the id recyclerView
                    val recyclerView: RecyclerView = findViewById(R.id.rv_follower)
                    recyclerView.layoutManager = LinearLayoutManager(this@FollowerListActivity)
                    recyclerView.adapter = adapter
                } else {
                    Log.d("fotest", "팔로워 리스트 실패"+response.message())
                }
            }

            override fun onFailure(call: Call<FollowingInfo>, t: Throwable) { // 변경된 부분
                Log.d("fotest", "팔로워 리스트 실패"+t.message)
            }
        })
    }

    fun getFollowerList() {
        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val userid = sp.getString("userId", null)
            val service = RetrofitClient.getRetrofitmain()
            val call = service.create(FollowListService::class.java).requestFollowerList(userid!!)
            call.enqueue(object : retrofit2.Callback<FollowerInfo>{ // 변경된 부분
                override fun onResponse(call: Call<FollowerInfo>, response: Response<FollowerInfo>) { // 변경된 부분
                    if (response.isSuccessful) {
                        val result = response.body()!!
                        val adapter = Follower_MyAdapter(this@FollowerListActivity, result) { uid ->
                            // Handle item click here if needed
                            Log.d("fotest", "언팔ㄱㄱ $uid")
                        }

                        // Assuming you have a RecyclerView with the id recyclerView
                        val recyclerView: RecyclerView = findViewById(R.id.rv_follower)
                        recyclerView.layoutManager = LinearLayoutManager(this@FollowerListActivity)
                        recyclerView.adapter = adapter
                    } else {
                        Log.d("fotest", "팔로워 리스트 실패"+response.message())
                    }
                }

                override fun onFailure(call: Call<FollowerInfo>, t: Throwable) { // 변경된 부분
                    Log.d("fotest", "팔로워 리스트 실패"+t.message)
                }
            })
    }
    fun getBFList() {
        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val userid = sp.getString("userId", null)
        val service = RetrofitClient.getRetrofitmain()
        val call = service.create(FollowListService::class.java).requestFollowerList(userid!!)
        call.enqueue(object : retrofit2.Callback<FollowerInfo>{ // 변경된 부분
            override fun onResponse(call: Call<FollowerInfo>, response: Response<FollowerInfo>) { // 변경된 부분
                if (response.isSuccessful) {
                    val result = response.body()!!
                    val adapter = Follower_MyAdapter(this@FollowerListActivity, result) { uid ->
                        // Handle item click here if needed
                        Log.d("fotest", "언팔ㄱㄱ $uid")
                    }

                    // Assuming you have a RecyclerView with the id recyclerView
                    val recyclerView: RecyclerView = findViewById(R.id.rv_follower)
                    recyclerView.layoutManager = LinearLayoutManager(this@FollowerListActivity)
                    recyclerView.adapter = adapter
                } else {
                    Log.d("fotest", "팔로워 리스트 실패"+response.message())
                }
            }

            override fun onFailure(call: Call<FollowerInfo>, t: Throwable) { // 변경된 부분
                Log.d("fotest", "팔로워 리스트 실패"+t.message)
            }
        })
    }
    private fun onMenuSelected(selectedTextView: TextView) {
        when (selectedTextView.id) {
            R.id.textFollowing -> {
                getFollowingList()
            }

            R.id.textFollower -> {
                getFollowerList()
            }
//            R.id.textFriends -> {
//                clearTextStyles()
//                applySelectedTextStyle(selectedTextView)
//            }
        }
        // 모든 텍스트뷰의 텍스트 스타일 초기화
        textFollowing.setTypeface(null, Typeface.NORMAL)
        textFollower.setTypeface(null, Typeface.NORMAL)
        textFriends.setTypeface(null, Typeface.NORMAL)

        // 선택된 텍스트뷰에 강조 효과 적용
        selectedTextView.setTypeface(null, Typeface.BOLD)
        // 여기에서 적절한 강조 색상 설정을 추가하세요.
        // 선택된 텍스트뷰에 강조 효과 적용
        selectedTextView.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")))
        selectedTextView.setTypeface(null, Typeface.BOLD)
    }

    private fun clearTextStyles() {
        // 모든 텍스트뷰의 텍스트 스타일 초기화
        // 여기서는 각 텍스트뷰의 텍스트 스타일을 bold에서 normal로 변경
        textFollower.setTypeface(null, Typeface.NORMAL)
        textFollowing.setTypeface(null, Typeface.NORMAL)
        textFriends.setTypeface(null, Typeface.NORMAL)

        textFollower.setTextColor(ColorStateList.valueOf(Color.parseColor("#4D4D4D")))
        textFollowing.setTextColor(ColorStateList.valueOf(Color.parseColor("#4D4D4D")))
        textFriends.setTextColor(ColorStateList.valueOf(Color.parseColor("#4D4D4D")))
    }

    fun unFollow(uid:String){
        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val userid = sp.getString("userId", null)
        val call = RetrofitClient.getRetrofitmain().create(FollowListService::class.java).unfollow(userid.toString() ,uid)
        call.enqueue(object : retrofit2.Callback<message> {
            override fun onResponse(call: Call<message>, response: Response<message>) {
                if (response.isSuccessful) {
                    Log.d("fotest", "언팔 성공")
                } else {
                    Log.d("fotest", "언팔 실패"+response.message())
                }
            }

            override fun onFailure(call: Call<message>, t: Throwable) {
                Log.d("fotest", "언팔 실패"+t.message)
            }
        })
    }

}