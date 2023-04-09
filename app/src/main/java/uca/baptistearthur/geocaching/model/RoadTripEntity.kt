package uca.baptistearthur.geocaching.model

import android.provider.Telephony.Mms.Addr
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import org.osmdroid.util.GeoPoint
import java.util.Date

@Entity(tableName = "Roadtrip")
class RoadTripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="date") val date: Date,
    @ColumnInfo(name="places") val places: MutableList<Place>
){
    fun addPlaceToRoadTripList(place: Place) = places.add(place)
    fun addPlaceToRoadTripList(latitude: Double, longitude: Double) = places.add(Place(latitude, longitude))
    fun toJSON(): String = Gson().toJson(this)

}