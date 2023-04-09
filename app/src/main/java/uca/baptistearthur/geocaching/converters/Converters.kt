package uca.baptistearthur.geocaching.converters

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uca.baptistearthur.geocaching.model.Address
import uca.baptistearthur.geocaching.model.Place
import uca.baptistearthur.geocaching.model.RoadTripEntity
import java.util.*

class Converters {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toString(places: List<Place>): String {
        return Gson().toJson(places)
    }

    @TypeConverter
    fun toPlaces(value: String): List<Place> {
        val listType = object : TypeToken<List<Place>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toRoadTripEntity(value: String?): RoadTripEntity {
        return Gson().fromJson(value, RoadTripEntity::class.java)
    }

}

fun Date.toFrenchFormat(): String {
    val day: String = if(this.date < 10) "0${this.date}" else "${this.date}"
    val month: String = if(this.month < 10) "0${this.month+1}" else "${this.month+1}"
    val year = "${this.year + 1900}"
    val hours: String = if(this.hours < 10) "0${this.hours}" else "${this.hours}"
    val minutes: String = if(this.minutes < 10) "0${this.minutes}" else "${this.minutes}"

    return "$day/$month/$year - ${hours}h$minutes"}