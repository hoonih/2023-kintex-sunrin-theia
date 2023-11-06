package com.example.thiea.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.thiea.R
import com.example.thiea.databinding.ActivityMainBinding
import com.example.thiea.databinding.ActivitySplashBinding
import com.example.thiea.ui.login.LoginActivity
import com.example.thiea.ui.main.MainActivity
import com.kakao.sdk.common.util.Utility

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Log.d("Theia", "keyhash : ${Utility.getKeyHash(this)}")


        val sp = getSharedPreferences("autoLogin", MODE_PRIVATE);
        val userid = sp.getString("userId", null)
        if (userid == null) {
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, 3000)
        }
        else {
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, 3000)
        }

    }
}