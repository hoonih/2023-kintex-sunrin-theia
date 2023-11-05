package com.example.thiea.ui.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentCompleteBinding
import com.example.thiea.ui.login.LoginActivity


class CompleteFragment : Fragment() {

    private var _binding: FragmentCompleteBinding? = null
    private val binding: FragmentCompleteBinding
        get() = requireNotNull(_binding) {"바인딩 객체를 생성해주세요."}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNext.setOnClickListener {
            (activity as LoginActivity).back()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}