package com.example.thiea.ui.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thiea.R
import com.example.thiea.databinding.FragmentGenderBinding
import com.example.thiea.ui.login.LoginActivity


class GenderFragment : Fragment() {

    private var _binding : FragmentGenderBinding? = null
    private val binding: FragmentGenderBinding
        get() = requireNotNull(_binding) {"바인딩 객체가 생성되지 않았습니다."}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var genderselected: String = ""
        binding.btMale.setOnClickListener {
            genderselected = "male"
            genderselect("male")
        }
        binding.btFemale.setOnClickListener {
            genderselected = "female"
            genderselect("female")
        }
        binding.btNoselect.setOnClickListener {
            genderselected = "noselect"
            genderselect("noselect")
        }
        binding.btNext.setOnClickListener {
            if (genderselected != "") {
                (activity as LoginActivity).savegender(genderselected)
            }
        }

        binding.btBack.setOnClickListener {
            (activity as LoginActivity).replaceFragment(PhoneFragment())
        }

    }

    private fun genderselect(selected: String) {
        binding.btNext.setBackgroundResource(R.drawable.bt_register)
        binding.ivFemale.setImageResource(R.drawable.ic_unfemale)
        binding.ivMale.setImageResource(R.drawable.ic_unmale)
        binding.ivNoselect.setImageResource(R.drawable.ic_unnoselect)

        when(selected) {
            "male" -> binding.ivMale.setImageResource(R.drawable.ic_male)
            "female" -> binding.ivFemale.setImageResource(R.drawable.ic_female)
            else -> binding.ivNoselect.setImageResource(R.drawable.ic_noselect)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}