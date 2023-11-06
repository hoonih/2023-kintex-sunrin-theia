package com.example.thiea.ui.main.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentEmotionDialogBinding
import com.example.thiea.databinding.FragmentTextDialogBinding
import com.example.thiea.ui.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TextDialogFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentTextDialogBinding? = null
    private val binding: FragmentTextDialogBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTextDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBack2.setOnClickListener {
            (activity as MainActivity).bottomDialog(EmotionDialogFragment())
            dismiss()
        }
        binding.btBack.setOnClickListener {
            dismiss()
        }
        binding.btConfirm.setOnClickListener {
            (activity as MainActivity).setposttext(binding.etWrite.text.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}