package uca.baptistearthur.geocaching.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay
import android.util.Log
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import uca.baptistearthur.geocaching.R

open class Map : Fragment() {
    protected lateinit var map : MapView
    companion object{
        const val minimumZoomLevel = 4.0
        const val defaultZoomLevel = 21.0
        const val userAgent = "RoadTrip"
        val defaultPoint = GeoPoint(48.8583, 2.2944)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = userAgent;
    }
    protected fun configureMap() = map.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            minZoomLevel = minimumZoomLevel
            controller.apply {
                setCenter(defaultPoint)
                setZoom(defaultZoomLevel)
            }
            addMapOverlays(mapView = this)
        }

    open fun addMapOverlays(mapView: MapView){
        // Compass Overlay
        val compassOverlay = CompassOverlay(context, InternalCompassOrientationProvider(context), map);
        compassOverlay.enableCompass();
        map.overlays.add(compassOverlay);

        // Scale Bar Overlay
        val scaleBarOverlay = ScaleBarOverlay(map)
        scaleBarOverlay.setAlignRight(true)
        map.overlays.add(scaleBarOverlay)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("GeoMap", "MAP ON CREATE VIEW")
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        map = view.findViewById(R.id.mapView)
        configureMap()
        return view
    }

    override fun onResume() {
        super.onResume()
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }
}