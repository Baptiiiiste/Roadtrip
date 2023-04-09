package uca.baptistearthur.geocaching.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import uca.baptistearthur.geocaching.R
import uca.baptistearthur.geocaching.model.RoadTripEntity


class RoadTripAdapter(val voyages: List<RoadTripEntity>, val navController: NavController) : RecyclerView.Adapter<RoadTripViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadTripViewHolder {
        return RoadTripViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_one_roadtrip, parent, false), navController)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RoadTripViewHolder, position: Int) {
        holder.roadTripAccessButton.text = ">  " + if (voyages[position].name.length > 20) voyages[position].name.substring(0, 20) + "..." else voyages[position].name

        holder.clickedRoadTrip = voyages[position]
    }
    override fun getItemCount(): Int = voyages.size
}