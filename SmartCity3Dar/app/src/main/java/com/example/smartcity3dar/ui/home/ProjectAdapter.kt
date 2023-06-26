package com.example.smartcity3dar.ui.home

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.smartcity3dar.R
import com.example.smartcity3dar.models.ProjectData
import java.util.ArrayList;

class ProjectAdapter  // invoke the suitable constructor of the ArrayAdapter class
    (context: Context, arrayList: ArrayList<ProjectData>) :
    ArrayAdapter<ProjectData>(context, 0, arrayList) {
    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {

        // convertView which is recyclable view
        var currentItemView = convertView

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView =
                LayoutInflater.from(context).inflate(R.layout.fragment_project_list_item, parent, false)
        }

        // get the position of the view from the ArrayAdapter
        val currentProject: ProjectData? = getItem(position)

        println(currentProject)
        // then according to the position of the view assign the desired TextView 1 for the same
        val textView1 = currentItemView!!.findViewById<TextView>(R.id.projectName)
        textView1.setText(currentProject?.Name)

        // then according to the position of the view assign the desired TextView 1 for the same
        val textView2 = currentItemView!!.findViewById<TextView>(R.id.layer)
        textView2.setText(currentProject?.Description)

        // then according to the position of the view assign the desired TextView 1 for the same
        val textView3 = currentItemView!!.findViewById<TextView>(R.id.distance)
        textView3.setText(currentProject?.Id.toString())


        // then return the recyclable view
        return currentItemView
    }
}