package com.example.smartcity3dar.ui.dashboard

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity3dar.R
import com.example.smartcity3dar.databinding.FragmentDashboardBinding
import com.example.smartcity3dar.views.MapMarker
import org.json.JSONException
import org.json.JSONObject
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.ITileSource
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var map: MapView
    lateinit var mapController: IMapController
    val coordinates = mutableListOf<GeoPoint>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Configuration.getInstance().userAgentValue = "PacioTest"
        map = binding.mapView

        val tileSource : XYTileSource = XYTileSource("OSM", 0, 20,
            512, ".png" , arrayOf(
                "https://tile.openstreetmap.org/"
            )
        )
        val overlayTile : XYTileSource = XYTileSource("Digitarca", 5, 25,
            512, ".png" , arrayOf(
                "https://www.digitarca.it/_maps/calatafimi_mms/CALAMAP_02-05/"
            )
        )

        map.setTileSource(tileSource)
        map.setMultiTouchControls(true)
        mapController = map.controller
        mapController.setCenter(GeoPoint(37.914, 12.864))
        mapController.setZoom(20.0)
        // Add tiles layer
        val provider = MapTileProviderBasic(requireContext().applicationContext)
        provider.tileSource = overlayTile
        val tilesOverlay = TilesOverlay(provider, this.context)
        tilesOverlay.loadingBackgroundColor = Color.TRANSPARENT
        map.overlays.add(tilesOverlay)
// Assuming you already have the mapView instance set up
        parseGeoJson()
        val polyline = Polyline()
        polyline.width = 5f // Set the width of the polyline
        polyline.color = Color.RED // Set the color of the polyline
        polyline.setPoints(coordinates) // Add the coordinates to the polyline
        map.overlays.add(polyline) // Add the polyline overlay to the map


        val vectorDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_signpost_24)?.mutate()
        vectorDrawable?.let { drawable ->
            val desiredSize = 1500 // Specify the desired size in pixels
            drawable.setBounds(0, 0, desiredSize, desiredSize)

            val desiredColor = Color.RED // Specify the desired color
            drawable.setTint(desiredColor)}

        val markers = ArrayList<OverlayItem>()
// Example points of interest
        val point1 = MapMarker(
            GeoPoint(37.91349500411455, 12.860042348242956),
            "Marker 1",
            "This is marker 1",
            vectorDrawable!!
        )

        val point2 = MapMarker(
            GeoPoint(37.91363095384082, 12.861034291532178),
            "Marker 2",
            "This is marker 2",
            vectorDrawable!!
        )


// Add the points to the markers list
        markers.add(point1)
        markers.add(point2)

// Create an ItemizedIconOverlay with the markers list
        val itemizedIconOverlay = ItemizedIconOverlay<OverlayItem>(
            markers,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    // Handle marker click event here
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    // Handle marker long press event here
                    return true
                }
            },
            context
        )

        map.overlays.add(itemizedIconOverlay)

        map.invalidate() // Invalidate the map view to redraw the overlay
        return root
    }

    private fun resizeMarkerIcon(markerIcon: Drawable, width: Int, height: Int): Drawable {
        val resizedMarkerIcon = BitmapDrawable(
            resources,
            Bitmap.createScaledBitmap(
                (markerIcon as BitmapDrawable).bitmap,
                width,
                height,
                false
            )
        )
        resizedMarkerIcon.bounds = Rect(0, 0, width, height)
        return resizedMarkerIcon
    }

    private fun parseGeoJson(){
        val geoJson = """
    {
      "type": "FeatureCollection",
      "features": [
        {
          "type": "Feature",
          "properties": {},
          "geometry": {
            "coordinates": [
              [12.860042348242956, 37.91349500411455],
              [12.861034291532178, 37.91363095384082],
              [12.861834032000019, 37.91366755564731],
              [12.86199751485853, 37.91371461508618],
              [12.862012979452999, 37.91377561801717],
              [12.862390757409514, 37.91385405028315],
              [12.863232473206068, 37.91395339769983],
              [12.863782570932074, 37.914010914564315],
              [12.863802453982629, 37.91405100204898],
              [12.863484325176671, 37.91436647236223],
              [12.863384900056843, 37.914492573631776],
              [12.86287570521452, 37.91425878631395],
              [12.861786362149445, 37.91403816943081],
              [12.860409049938369, 37.91378879089626],
              [12.859873721818332, 37.91359193736005],
              [12.859769378200411, 37.9137351036202]
            ],
            "type": "LineString"
          }
        }
      ]
    }
""".trimIndent()



        try {
            val jsonObject = JSONObject(geoJson)
            val features = jsonObject.getJSONArray("features")
            val geometry = features.getJSONObject(0).getJSONObject("geometry")
            val lineString = geometry.getJSONArray("coordinates")

            for (i in 0 until lineString.length()) {
                val coordinate = lineString.getJSONArray(i)
                val longitude = coordinate.getDouble(0)
                val latitude = coordinate.getDouble(1)
                val geoPoint = GeoPoint(latitude, longitude)
                coordinates.add(geoPoint)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}