package com.example.smartcity3dar.ui.models

import com.google.gson.annotations.SerializedName

data class SessionModel(
    @SerializedName("SessionID")
    var SessionID : String,
    @SerializedName("Data")
    var Data : Data

)


data class Data(
    @SerializedName("IsAdmin")
    var IsAdmin : Boolean,
    @SerializedName("UserName")
    var UserName : String,
)