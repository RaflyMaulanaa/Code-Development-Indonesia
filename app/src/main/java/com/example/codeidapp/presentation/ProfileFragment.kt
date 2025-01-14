package com.example.codeidapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.codeidapp.databinding.FragmentProfileBinding
import com.example.codeidapp.viewmodel.ProfileViewModel
import com.example.codeidapp.viewmodel.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            doUpdate()
        }

        return binding.root
    }

    private fun doUpdate() {
        val result = viewModel.updateProfile(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString()
        )
        if (result) {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}