package uca.baptistearthur.geocaching.ui.overlay

import android.graphics.Canvas
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import uca.baptistearthur.geocaching.model.RoadTripEntity

class EditMarkerOverlay(roadManager: RoadManager, var roadTrip: RoadTripEntity) : MarkerOverlay<EditRoadtripOverlay>(roadManager) {

    fun addPlaces(geopoints: Collection<GeoPoint>, mapView: MapView)=
        geopoints.forEach {
            addMarkerAtGeopoint(it, mapView)
        }

    override fun createNewConfirmationOverlay(): EditRoadtripOverlay = EditRoadtripOverlay(places, roadTrip)
}