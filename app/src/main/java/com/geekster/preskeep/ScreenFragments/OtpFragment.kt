package com.geekster.preskeep.ScreenFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.geekster.preskeep.R
import com.geekster.preskeep.ViewModel.AuthViewModel
import com.geekster.preskeep.databinding.FragmentOtpBinding
import com.geekster.preskeep.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OtpFragment : Fragment() {

    private var _binding : FragmentOtpBinding? = null

    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonVerify.setOnClickListener {
            Toast.makeText(context, "Toast", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}