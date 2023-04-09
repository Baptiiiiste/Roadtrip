package uca.baptistearthur.geocaching.recyclerview

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.osmdroid.util.GeoPoint
import uca.baptistearthur.geocaching.R
import uca.baptistearthur.geocaching.model.Place


class PlacesAdapter (val places: List<Place>) : RecyclerView.Adapter<PlacesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        return PlacesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_place, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.placeAddress.text = places[position].address.displayName
    }
    override fun getItemCount(): Int = places.size
}


