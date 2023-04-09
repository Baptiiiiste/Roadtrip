package uca.baptistearthur.geocaching.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import uca.baptistearthur.geocaching.model.RoadTripEntity
import uca.baptistearthur.geocaching.ui.overlay.EditMarkerOverlay
import kotlin.math.ln
import kotlin.math.roundToInt
import kotlin.math.sqrt


class EditRoadtripMap : Map() {

    var roadTrip: RoadTripEntity? = null
    set(value) {
        roadTrip?:
            value?.let {
                addEditMarkerOverlay(value)
                getMapParams(value).let{
                    map.controller.setCenter(it.first)
                    map.controller.setZoom(it.second)
                }
                field = value
            }
    }

    private fun addEditMarkerOverlay(roadTrip: RoadTripEntity){
        val editMarkerOverlay = EditMarkerOverlay(OSRMRoadManager(context, userAgent), roadTrip)
        editMarkerOverlay.addPlaces(roadTrip.places, map)
        map.overlays.add(editMarkerOverlay);
    }

    private fun getMapParams(roadTrip: RoadTripEntity): Pair<GeoPoint, Double>{
        val boundingBox = BoundingBox.fromGeoPoints(roadTrip.places)
        val maxDistance = 7000000.0
        val logZoom = (defaultZoomLevel - minimumZoomLevel) * ln(boundingBox.diagonalLengthInMeters) / ln(maxDistance)
        val zoomLevel = (defaultZoomLevel - logZoom).coerceIn(minimumZoomLevel, defaultZoomLevel)
        return Pair(boundingBox.centerWithDateLine, zoomLevel)
    }

}