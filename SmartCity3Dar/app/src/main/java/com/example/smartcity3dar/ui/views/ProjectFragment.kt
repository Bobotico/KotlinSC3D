package com.example.smartcity3dar.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity3dar.databinding.AvailableProjectsBinding
import com.example.smartcity3dar.databinding.ModalLoginBinding
import com.example.smartcity3dar.ui.home.LoginViewModel
import com.example.smartcity3dar.ui.viewModels.ProjectViewModel


class ProjectFragment : Fragment() {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    private lateinit var viewModel: ProjectViewModel

    private var _binding: AvailableProjectsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(ProjectViewModel::class.java)

        _binding = AvailableProjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //binding.projectsScrollView

        return root
    }

}