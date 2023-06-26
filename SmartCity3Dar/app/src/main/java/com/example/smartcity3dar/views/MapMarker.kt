package com.example.smartcity3dar.views

import android.graphics.drawable.Drawable
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.OverlayItem

class MapMarker(
    point: GeoPoint,
    title: String,
    snippet: String,
    markerIcon: Drawable
) : OverlayItem(title, snippet, point) {
    init {
        setMarker(markerIcon)
    }
}