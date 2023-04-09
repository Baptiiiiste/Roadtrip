package uca.baptistearthur.geocaching.ui.overlay

import android.content.Context
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.Polyline
import uca.baptistearthur.geocaching.R


abstract class MarkerOverlay<T : ConfirmationOverlay>(val roadManager: RoadManager) : Overlay() {

    protected var places: MutableSet<PlaceMarker> = mutableSetOf()
    private lateinit var roadOverlay: Polyline
    private var confirmationOverlayIsVisible = false;

    protected fun addMarkerAtGeopoint(geoPoint: GeoPoint, mapView: MapView){
        val marker = PlaceMarker(mapView, this)
        marker.position = geoPoint
        if(places.isNotEmpty()) places.last().setDefaultIcon()
        places.add(marker)
        places.forEach{ it.closeInfoWindow()}
        computeIcons(mapView.context)
        mapView.overlays.add(marker)
        computeRoad(mapView)
        computeConfirmationOverlay(mapView);
        mapView.invalidate()
    }

    override fun onDoubleTap(e: MotionEvent?, mapView: MapView?): Boolean {

        val proj = mapView?.projection;
        if(proj!=null){
            val geoPoint = proj.fromPixels(e?.x?.toInt()!!, e.y.toInt() ) as GeoPoint
            addMarkerAtGeopoint(geoPoint, mapView)
        }
        return true;
    }

    fun computeIcons(context: Context) {
        if (places.isNotEmpty()) {
            val flagIcon = ContextCompat.getDrawable(context, R.drawable.roadtrip_marker)!!
            places.last().icon = flagIcon
            places.first().icon = flagIcon
        }
    }

    fun computeRoad(mapView: MapView) {
        mapView.overlays.removeAll{ it is Polyline }
        if (places.size > 1) {
            CoroutineScope(Dispatchers.IO).launch {
                val road = roadManager.getRoad(ArrayList(places.map{it.position}))
                withContext(Dispatchers.Main) {
                    roadOverlay = RoadManager.buildRoadOverlay(road)
                    mapView.overlays.add(roadOverlay)
                }
            }
        }
    }

    abstract fun createNewConfirmationOverlay(): T

    fun computeConfirmationOverlay(mapView: MapView){
        if (places.size > 1) {
            if(!confirmationOverlayIsVisible) {
                mapView.overlays.add(createNewConfirmationOverlay())
                confirmationOverlayIsVisible = true
            }
        }else{
            mapView.overlays.removeAll{ it is ConfirmationOverlay }
            confirmationOverlayIsVisible=false
        }
    }
    fun removeMarker(placeMarker: PlaceMarker) = places.remove(placeMarker);
    fun getMarkerLabel(placeMarker: PlaceMarker) =
        when (placeMarker) {
            places.first() -> {
                "Start"
            }
            places.last() -> {
                "Finish"
            }
            else -> {
                "Step " + places.indexOf(placeMarker);
            }
        }

}