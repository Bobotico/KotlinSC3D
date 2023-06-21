package com.example.smartcity3dar.models

import com.google.gson.annotations.SerializedName

data class AssetModel(
    @SerializedName("SessionID")
    var SessionID : String,
    @SerializedName("Data")
    var Data : Array<AssetData>

)

data class AssetData(
    @SerializedName("Id")
    var Id : Int,
    @SerializedName("AssetType")
    var AssetType : Int,
    @SerializedName("LayerId")
    var LayerId : Int,
    @SerializedName("SyncId")
    var SyncId : String,
    @SerializedName("Name")
    var Name : String,
    @SerializedName("Geometry")
    var Geometry : String,
    @SerializedName("Geography")
    var Geography : String,
)
