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
        binding.pingFear.setOnClickListener {
            emotionselected = 4
            select("fear")
        }
        binding.pingExpect.setOnClickListener {
            emotionselected = 5
            select("expect")
        }
        binding.pingHate.setOnClickListener {
            emotionselected = 6
            select("hate")
        }
        binding.pingTrust.setOnClickListener {
            emotionselected = 7
            select("trust")
        }
        binding.pingAmaze.setOnClickListener {
            emotionselected = 8
            select("amaze")
        }
    }

    private fun select(selected: String) {
        nowselected = true
        binding.btConfirm.setBackgroundResource(R.drawable.bt_confirm)
        binding.ivHappy.setImageResource(R.drawable.ping_happy)
        binding.ivSad.setImageResource(R.drawable.ping_sad)
        binding.ivAngry.setImageResource(R.drawable.ping_angry)
        binding.ivFear.setImageResource(R.drawable.ping_fear)
        binding.ivHate.setImageResource(R.drawable.ping_hate)
        binding.ivExpect.setImageResource(R.drawable.ping_expect)
        binding.ivTrust.setImageResource(R.drawable.ping_trust)
        binding.ivAmaze.setImageResource(R.drawable.ping_amaze)
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
            "fear" -> {
                binding.ivFear.setImageResource(R.drawable.ping_fear_check)
                (activity as MainActivity).setemotion(4)
            }
            "expect" -> {
                binding.ivExpect.setImageResource(R.drawable.ping_expect_check)
                (activity as MainActivity).setemotion(5)
            }
            "hate" -> {
                binding.ivHate.setImageResource(R.drawable.ping_hate_check)
                (activity as MainActivity).setemotion(6)
            }
            "trust" -> {
                binding.ivTrust.setImageResource(R.drawable.ping_trust_check)
                (activity as MainActivity).setemotion(7)
            }
            else -> {
                binding.ivAmaze.setImageResource(R.drawable.ping_amaze_check)
                (activity as MainActivity).setemotion(8)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}