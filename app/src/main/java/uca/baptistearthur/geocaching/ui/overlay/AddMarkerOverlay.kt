package uca.baptistearthur.geocaching.ui.overlay

import org.osmdroid.bonuspack.routing.RoadManager
import uca.baptistearthur.geocaching.model.RoadTripEntity

class AddMarkerOverlay(roadManager: RoadManager) : MarkerOverlay<NewRoadtripOverlay>(roadManager) {
    override fun createNewConfirmationOverlay(): NewRoadtripOverlay = NewRoadtripOverlay(places)
}