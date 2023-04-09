package uca.baptistearthur.geocaching.ui.overlay

import android.app.AlertDialog
import android.content.Context
import android.text.InputFilter
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import uca.baptistearthur.geocaching.R
import uca.baptistearthur.geocaching.application.RTApplication
import uca.baptistearthur.geocaching.model.Place
import uca.baptistearthur.geocaching.model.RoadTripEntity
import uca.baptistearthur.geocaching.viewModels.RoadTripViewModel
import uca.baptistearthur.geocaching.viewModels.RoadTripViewModelFactory
import java.util.*

class EditRoadtripOverlay(points: MutableCollection<PlaceMarker>, val roadTripEntity: RoadTripEntity) : ConfirmationOverlay(points) {

    fun getRoadTripViewModelFromOverlay(overlayContext: Context): RoadTripViewModel {
        val roadTripDao = (overlayContext.applicationContext as RTApplication).db.roadTripDAO()
        val viewModelFactory = RoadTripViewModelFactory(roadTripDao)
        return ViewModelProvider(overlayContext as ViewModelStoreOwner, viewModelFactory).get(
            RoadTripViewModel::class.java)
    }

    private fun onValidation(mapView: MapView, input: String) {
        val places: MutableList<Place> =
            points.map { Place(it.position.latitude, it.position.longitude) }.toMutableList()
        CoroutineScope(Dispatchers.Main).launch {
            places.filter { it.address.displayName === "unknown" }.forEach { it.initAddress() }
            val newRoadTrip = RoadTripEntity(
                id = roadTripEntity.id,
                name = input,
                date = roadTripEntity.date,
                places = places
            )
            getRoadTripViewModelFromOverlay(mapView.context).deleteRoadTrip(roadTripEntity)
            getRoadTripViewModelFromOverlay(mapView.context).insertRoadTrip(newRoadTrip)
            Toast.makeText(
                mapView.context,
                R.string.changesSaved,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun confirm(mapView: MapView) {
        val input = EditText(mapView.context)
        input.setText(roadTripEntity.name)
        input.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(50))

        val dialog = AlertDialog.Builder(mapView.context)
            .setTitle(R.string.newRoadtripDialog)
            .setView(input)
            .setPositiveButton(R.string.confirm) { _, _ ->
                val userInput = input.text.toString()
                if (userInput.isNotBlank()) {
                    onValidation(mapView, input.text.toString())
                } else {
                    Toast.makeText(
                        mapView.context,
                        R.string.emptyTextError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
        dialog.show()
    }
}