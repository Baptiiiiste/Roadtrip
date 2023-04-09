package uca.baptistearthur.geocaching.recyclerview

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uca.baptistearthur.geocaching.R

class PlacesViewHolder(val cellule: View): ViewHolder(cellule) {

    var placeAddress: TextView = cellule.findViewById(R.id.txtPlaceAddress)

}