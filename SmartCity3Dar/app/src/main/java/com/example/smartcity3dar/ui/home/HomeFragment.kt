package com.example.smartcity3dar.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.core.view.contains
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartcity3dar.R
import com.example.smartcity3dar.databinding.FragmentHomeBinding
import com.example.smartcity3dar.models.ProjectData
import com.example.smartcity3dar.models.ProjectModel
import com.example.smartcity3dar.viewModels.ProjectViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var projectViewModel : ProjectViewModel

    private var projectsLoaded : ProjectModel? = null

    private lateinit var listView : ListView

    private lateinit var projectNames : ArrayList<String>

    private lateinit var adapter : ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        projectViewModel =
            ViewModelProvider(this).get(ProjectViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val searchBar : SearchView = root.findViewById(R.id.searchBar);
        listView = root.findViewById(R.id.projectList)
        val refreshButton : FloatingActionButton = root.findViewById(R.id.refreshButton)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(projectNames.contains(query)) {
                    adapter.filter.filter(query)
                }else{
                    println("No query eligible")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)
                return false
            }
        })
        refreshButton.setOnClickListener {
            loadProjectList()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadProjectList() {
        projectViewModel.viewModelScope.launch(Dispatchers.Default) {
            var loadProjectTask = async { projectViewModel.loadProjects()}
            fillProjectList(loadProjectTask.await())
        }

    }



    suspend fun fillProjectList(projectList : ProjectModel?) = withContext(Dispatchers.Main){
        if(projectList != null){
            projectNames = ArrayList()

            for(project in projectList!!.Data){
                // Creazione pulsanti progetti.
                projectNames.add(project.Name)
            }
            adapter = ArrayAdapter<String>(requireView().context, android.R.layout.simple_list_item_1, projectNames)
            listView.adapter = adapter
        }
    }
}