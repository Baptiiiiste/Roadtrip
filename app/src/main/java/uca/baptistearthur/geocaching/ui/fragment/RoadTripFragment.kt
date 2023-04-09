package uca.baptistearthur.geocaching.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uca.baptistearthur.geocaching.R
import uca.baptistearthur.geocaching.application.RTApplication
import uca.baptistearthur.geocaching.model.Place
import uca.baptistearthur.geocaching.model.RoadTripEntity
import uca.baptistearthur.geocaching.recyclerview.RoadTripAdapter
import uca.baptistearthur.geocaching.viewModels.RoadTripViewModel
import uca.baptistearthur.geocaching.viewModels.RoadTripViewModelFactory
import java.util.*

class RoadTripFragment : Fragment() {
    private var roadTripRecyclerView : RecyclerView? = null

    private val roadTripViewModel: RoadTripViewModel by viewModels<RoadTripViewModel> {
        RoadTripViewModelFactory((requireActivity().application as RTApplication).db.roadTripDAO())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_roadtrip, container, false)

        roadTripRecyclerView = view?.findViewById(R.id.recyclerViewRoadTripList)

        roadTripViewModel.getAllRoadTrips().observe(viewLifecycleOwner, { roadTrips ->

            if(roadTrips.isEmpty()){
                Toast.makeText(
                    context,
                    R.string.noRoadTripFound,
                    Toast.LENGTH_SHORT
                ).show()
            }else roadTripRecyclerView?.adapter = RoadTripAdapter(roadTrips, findNavController())
        })

        roadTripRecyclerView?.layoutManager = LinearLayoutManager(context)

        return view
    }
}