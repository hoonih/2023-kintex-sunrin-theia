package com.example.thiea.ui.main.dialog

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.thiea.BuildConfig
import com.example.thiea.R
import com.example.thiea.databinding.FragmentCompleteDialogBinding
import com.example.thiea.databinding.FragmentEmotionDialogBinding
import com.example.thiea.databinding.FragmentPingDialogBinding
import com.example.thiea.ui.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

class PingDialogFragment : BottomSheetDialogFragment() {

    var _binding : FragmentPingDialogBinding? = null
    private val binding: FragmentPingDialogBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPingDialogBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etWrite.text = (activity as MainActivity).des()

        val glideUrl = GlideUrl(BuildConfig.Base_URL+(activity as MainActivity).url())

        Glide.with(this)
            .load(glideUrl)
            .fitCenter()
            .apply(RequestOptions().override(200, 200))
            .transform(CenterCrop()  , RoundedCorners(8))
            .into(binding.btImage)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}