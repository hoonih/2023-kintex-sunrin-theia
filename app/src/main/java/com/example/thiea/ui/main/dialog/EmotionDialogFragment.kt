package com.example.thiea.ui.main.dialog

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentEmotionDialogBinding
import com.example.thiea.databinding.FragmentPhoneBinding
import com.example.thiea.ui.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EmotionDialogFragment : BottomSheetDialogFragment() {

    private var emotionselected : Int = 0
    private var nowselected = false
    private var _binding : FragmentEmotionDialogBinding? = null
    private val binding: FragmentEmotionDialogBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmotionDialogBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btConfirm.setOnClickListener {
            if (nowselected == true){
                (activity as MainActivity).bottomDialog(TextDialogFragment())
                dismiss()
            }
        }
        binding.btBack.setOnClickListener {
            dismiss()
        }
        binding.pingHappy.setOnClickListener {
            emotionselected = 1
            select("happy")
        }

        binding.pingSad.setOnClickListener {
            emotionselected = 2
            select("sad")
        }
        binding.pingAngry.setOnClickListener {
            emotionselected = 3
            select("angry")
        }
        binding.pingAmaze.setOnClickListener {
            emotionselected = 4
            select("amaze")
        }
        binding.pingTired.setOnClickListener {
            emotionselected = 5
            select("tired")
        }
    }

    private fun select(selected: String) {
        nowselected = true
        binding.btConfirm.setBackgroundResource(R.drawable.bt_register)
        binding.ivHappy.setImageResource(R.drawable.ping_happy)
        binding.ivSad.setImageResource(R.drawable.ping_sad)
        binding.ivAngry.setImageResource(R.drawable.ping_angry)
        binding.ivAmaze.setImageResource(R.drawable.ping_amaze)
        binding.ivTired.setImageResource(R.drawable.ping_tired)
        when(selected) {
            "happy" -> {
                binding.ivHappy.setImageResource(R.drawable.ping_happy_check)
                (activity as MainActivity).setemotion(1)
            }
            "sad" -> {
                binding.ivSad.setImageResource(R.drawable.ping_sad_check)
                (activity as MainActivity).setemotion(2)
            }
            "angry" -> {
                binding.ivAngry.setImageResource(R.drawable.ping_angry_check)
                (activity as MainActivity).setemotion(3)
            }
            "amaze" -> {
                binding.ivAmaze.setImageResource(R.drawable.ping_amaze_check)
                (activity as MainActivity).setemotion(4)
            }
            else -> {
                binding.ivTired.setImageResource(R.drawable.ping_tired_check)
                (activity as MainActivity).setemotion(5)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}