package com.example.thiea.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentNameBinding
import com.example.thiea.ui.login.LoginActivity


class NameFragment : Fragment() {
    private var _binding: FragmentNameBinding?= null
    private val binding: FragmentNameBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNext.setOnClickListener {
            if (binding.etName.text.toString() != "") {
                (activity as LoginActivity).replaceFragment(PhoneFragment())
                (activity as LoginActivity).savename(binding.etName.text.toString())
            }
        }
        binding.ivBack.setOnClickListener {
            (activity as LoginActivity).back()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}