package uca.baptistearthur.geocaching.ui.overlay

import android.view.MotionEvent
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class PlaceMarker(val mapView: MapView, val parent : MarkerOverlay<*>) : Marker(mapView) {

    override fun getTitle() = parent.getMarkerLabel(this)
    override fun onLongPress(e: MotionEvent?, mapView: MapView?): Boolean {
        if(mapView!=null && this.hitTest(e, mapView)) {
            parent.removeMarker(this)
            this.closeInfoWindow()
            mapView.overlays.remove(this)
            parent.computeIcons(mapView.context)
            parent.computeRoad(mapView)
            parent.computeConfirmationOverlay(mapView)
            mapView.invalidate()
            return true
        }
        return false
    }

}