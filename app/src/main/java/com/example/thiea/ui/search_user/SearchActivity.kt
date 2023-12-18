package com.example.thiea.ui.search_user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thiea.R
import com.example.thiea.data.model.userSearch
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.SearchUserService

class SearchActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val Recyclerview = findViewById<RecyclerView>(R.id.recycle_search)
        Recyclerview.layoutManager = LinearLayoutManager(this)
        val User_id = findViewById<EditText>(R.id.txt_search)
        User_id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트 변경 전에 호출되는 메서드
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 호출되는 메서드
            }

            override fun afterTextChanged(editable: Editable?) {
                Log.d("fortest", "afterTextChanged: ${editable.toString()}")
                val enteredText = editable.toString()
                val Retrofit = RetrofitClient.getRetrofitmain()
                var call = Retrofit.create(SearchUserService::class.java).search_users_by_name(enteredText)
                call.enqueue(object : retrofit2.Callback<List<userSearch>> {
                    override fun onResponse(call: retrofit2.Call<List<userSearch>>, response: retrofit2.Response<List<userSearch>>) {
                        if (response.isSuccessful) {
                            val userList = response.body()
                            userList?.let {
                                Recyclerview.adapter = RecyclerViewAdapter(this@SearchActivity, it)

                            }
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<List<userSearch>>, t: Throwable) {
                        // 통신 실패 로직
                    }
                })

            }
        })

    }


}