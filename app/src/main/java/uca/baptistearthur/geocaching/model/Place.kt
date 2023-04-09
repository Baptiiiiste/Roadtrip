package uca.baptistearthur.geocaching.model
import android.util.Log
import kotlinx.coroutines.*
import org.osmdroid.util.GeoPoint
import uca.baptistearthur.geocaching.network.AddressNetwork

class Place(private val lat: Double,
            private val lon: Double,
            var address: Address = Address("unknown", "unknown"))
    : GeoPoint(lat, lon){
    suspend fun initAddress() {
        AddressNetwork.retrofit.let {
            CoroutineScope(Dispatchers.IO).async {
                address = it.getAddress(lat, lon)
            }.await()
        }
    }
}