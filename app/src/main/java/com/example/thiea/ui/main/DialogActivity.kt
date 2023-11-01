package com.example.thiea.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.thiea.R
import com.example.thiea.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}