package uca.baptistearthur.geocaching.recyclerview

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uca.baptistearthur.geocaching.R
import uca.baptistearthur.geocaching.converters.Converters
import uca.baptistearthur.geocaching.model.RoadTripEntity


class RoadTripViewHolder(val cellule: View, val navController: NavController): ViewHolder(cellule) {

    var roadTripAccessButton: Button = cellule.findViewById(R.id.btnGetRoadTripsInfo)
    var clickedRoadTrip: RoadTripEntity? = null

    init{
        roadTripAccessButton.setOnClickListener{
            Log.d("RoadTripViewHolder", "RoadTripViewHolder clicked: ${clickedRoadTrip?.name}")
            var clickRoadTripJSON = clickedRoadTrip?.toJSON()
//            navController.navigate(R.id.action_roadTripFragment_to_roadtripDetail)

            val bundle = bundleOf("roadTrip" to clickRoadTripJSON)

            navController.navigate(R.id.action_roadTripFragment_to_roadtripDetail, bundle)

            Log.d("RoadTripViewHolder", "Data sent: ${clickedRoadTrip?.name}")

        }

    }

}