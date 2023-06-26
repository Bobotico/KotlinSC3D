package com.example.smartcity3dar.models

import com.google.gson.annotations.SerializedName

data class ProjectModel(
    @SerializedName("SessionID")
    var SessionID : String,
    @SerializedName("Data")
    var Data : Array<ProjectData>

)


data class ProjectData(
    @SerializedName("Id")
    var Id : Int,
    @SerializedName("SyncId")
    var SyncId : String,
    @SerializedName("Name")
    var Name : String,
    @SerializedName("Description")
    var Description : String,
    @SerializedName("LocationWGS84")
    var LocationWGS84 : String,
    @SerializedName("LayerId")
    var LayerId : Int,
)
