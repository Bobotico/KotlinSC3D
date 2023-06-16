package com.example.smartcity3dar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity3dar.databinding.FragmentLoginBinding
import com.example.smartcity3dar.ui.models.SessionModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val userNameTF: EditText = binding.userNameTF
        val passTF: EditText = binding.passwordTF
        val loginButton: Button = binding.loginButton
        loginButton.setOnClickListener {
            val inputUN = userNameTF.text.toString()
            val inputPass = passTF.text.toString()
            loginViewModel.login(inputUN, inputPass)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}