package com.example.thiea.ui.main.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentCompleteDialogBinding
import com.example.thiea.databinding.FragmentEmotionDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CompleteDialogFragment : BottomSheetDialogFragment() {

    var _binding : FragmentCompleteDialogBinding? = null
    private val binding: FragmentCompleteDialogBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompleteDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btConfirm.setOnClickListener {
            dismiss()
        }
        binding.btBack.setOnClickListener {
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}