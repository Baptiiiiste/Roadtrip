package uca.baptistearthur.geocaching.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
//import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uca.baptistearthur.geocaching.data.RoadTripDAO
import uca.baptistearthur.geocaching.model.RoadTripEntity

class RoadTripViewModel(val dao: RoadTripDAO): ViewModel() {

    fun getRoadTripById(id: Int) = dao.getRoadTripById(id).asLiveData()

    fun getAllRoadTrips() = dao.getAllRoadTrips().asLiveData()

    fun insertRoadTrip(r: RoadTripEntity){
        viewModelScope.launch {
            dao.insertRoadTrip(r)
        }
    }

    fun deleteRoadTrip(r: RoadTripEntity){
        viewModelScope.launch {
            dao.deleteRoadTrip(r)
        }
    }

}