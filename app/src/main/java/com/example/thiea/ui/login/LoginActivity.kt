package com.example.thiea.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.thiea.BuildConfig.NATIVE_APP_KEY
import com.example.thiea.R
import com.example.thiea.data.model.Post
import com.example.thiea.data.model.User
import com.example.thiea.databinding.ActivityLoginBinding
import com.example.thiea.remote.RetrofitClient
import com.example.thiea.remote.service.LoginService
import com.example.thiea.ui.SplashActivity
import com.example.thiea.ui.login.fragment.CompleteFragment
import com.example.thiea.ui.login.fragment.NameFragment
import com.example.thiea.ui.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private val mContext: Context = this
    lateinit var binding: ActivityLoginBinding
    lateinit var Userid:String
    lateinit var Username:String
    lateinit var UserPhone:String
    lateinit var UserGender:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, NATIVE_APP_KEY)
        //로그인 버튼 눌렀을때
        binding.btKakao.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        newaccount(user?.id.toString())
                        Userid = user?.id.toString()
                    }
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            val context = this
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        UserApiClient.instance.me { user, error ->
                            newaccount(user?.id.toString())
                            Userid = user?.id.toString()
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }
    private fun newaccount(id: String) {
        val loginservice = RetrofitClient.getRetrofitmain().create(LoginService::class.java)

        loginservice.userregister(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful()) {
                    Log.d("theia", "나오지마")
                    logincomplete() // 나오면 안됨
                    val intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("theia", "error - body : $body")
                        if (response.code() == 404) {
                            replaceFragment(NameFragment())
                            Log.d("theia", "test")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("theia", "API FAIL: ${call}")
            }

        })
    }
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }
    private fun logincomplete() {
        UserApiClient.instance.me { user, error ->
            val auto = getSharedPreferences("autoLogin", MODE_PRIVATE)
            val autoLoginEdit = auto.edit()
            autoLoginEdit.putString("userId", user!!.id.toString())
            autoLoginEdit.putString("userName", user!!.kakaoAccount!!.profile!!.nickname.toString())
            autoLoginEdit.commit()
        }
    }

    private fun userregister() {
        val loginservice = RetrofitClient.getRetrofitmain().create(LoginService::class.java)

        val emptylist : List<Post> = listOf()

        val theiauser = User(Userid, Username, UserPhone, UserGender)

        Log.d("theia", theiauser.toString())

        loginservice.register(theiauser).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful()) {
                    logincomplete()
                    replaceFragment(CompleteFragment())
                }
                else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("theia", "error - body : $body")

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("theia", "API FAIL: ${call}")
            }

        })
    }

    fun savename(name: String) {
        Username = name
    }
    fun savephone(phone: String) {
        UserPhone = phone
    }
    fun savegender(gender: String) {
        UserGender = gender
        userregister()
    }

    fun back() {
        val intent = Intent(mContext, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

}