package uca.baptistearthur.geocaching.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uca.baptistearthur.geocaching.R
import uca.baptistearthur.geocaching.application.RTApplication
import uca.baptistearthur.geocaching.converters.Converters
import uca.baptistearthur.geocaching.converters.toFrenchFormat
import uca.baptistearthur.geocaching.model.RoadTripEntity
import uca.baptistearthur.geocaching.recyclerview.PlacesAdapter
import uca.baptistearthur.geocaching.viewModels.RoadTripViewModel
import uca.baptistearthur.geocaching.viewModels.RoadTripViewModelFactory
import java.time.format.DateTimeFormatter
import java.util.*

class RoadtripDetail : Fragment() {

    private var placesRecyclerView : RecyclerView? = null
    private val map: EditRoadtripMap = EditRoadtripMap()
    private lateinit var roadTrip: RoadTripEntity;
    private val roadTripViewModel: RoadTripViewModel by viewModels {
        RoadTripViewModelFactory((requireActivity().application as RTApplication).db.roadTripDAO())
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.roadtrip_detail, container, false)
        roadTrip = Converters().toRoadTripEntity(arguments?.getString("roadTrip"))
        childFragmentManager.beginTransaction()
            .add(R.id.roadTripDetailMapView, map)
            .commit()

        placesRecyclerView = view?.findViewById(R.id.recyclerViewPlacesList)
        placesRecyclerView?.adapter = PlacesAdapter(roadTrip.places)
        placesRecyclerView?.layoutManager = LinearLayoutManager(context)
        view?.findViewById<TextView>(R.id.roadTripDetailTitle)?.text = roadTrip.name
        view?.findViewById<TextView>(R.id.roadTripDetailDate)?.text = roadTrip.date.toFrenchFormat()

        view?.findViewById<Button>(R.id.btnDeleteRoadTrip)?.setOnClickListener {
            try{
                roadTripViewModel.deleteRoadTrip(roadTrip)
            }catch (e: Exception){
                Toast.makeText(
                    context,
                    R.string.roadTripDeleteError,
                    Toast.LENGTH_SHORT
                ).show()
            }finally {
                findNavController().popBackStack()
                Toast.makeText(
                    context,
                    R.string.roadTripDeleteConfirmation,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        map.roadTrip=roadTrip
    }
}