package com.example.thiea.ui.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentPhoneBinding
import com.example.thiea.ui.login.LoginActivity

class PhoneFragment : Fragment() {
    private var _binding : FragmentPhoneBinding? = null
    private val binding: FragmentPhoneBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNext.setOnClickListener {
            if (binding.etPhone.text.toString() != "") {
                (activity as LoginActivity).replaceFragment(GenderFragment())
                (activity as LoginActivity).savephone(binding.etPhone.text.toString())
            }
        }
        binding.ivBack.setOnClickListener {
            (activity as LoginActivity).replaceFragment(NameFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}