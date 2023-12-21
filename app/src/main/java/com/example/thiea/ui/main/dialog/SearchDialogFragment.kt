package com.example.thiea.ui.main.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentPingDialogBinding
import com.example.thiea.databinding.FragmentSearchDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SearchDialogFragment : BottomSheetDialogFragment() {

    var _binding : FragmentSearchDialogBinding? = null
    private val binding: FragmentSearchDialogBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchDialogBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}