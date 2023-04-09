package uca.baptistearthur.geocaching.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uca.baptistearthur.geocaching.data.RoadTripDAO

class RoadTripViewModelFactory(private val dao: RoadTripDAO): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoadTripViewModel::class.java)){
            return RoadTripViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}