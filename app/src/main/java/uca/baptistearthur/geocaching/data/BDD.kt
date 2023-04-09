package uca.baptistearthur.geocaching.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uca.baptistearthur.geocaching.converters.Converters
import uca.baptistearthur.geocaching.model.RoadTripEntity
import androidx.room.Database

@Database(entities = arrayOf(RoadTripEntity::class), version=1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BDD : RoomDatabase(){

    abstract fun roadTripDAO(): RoadTripDAO

    companion object{
        private var INSTANCE: BDD ?= null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this){
                val db = Room.databaseBuilder(context, BDD::class.java, "roadTripDB").build()
                INSTANCE = db
                INSTANCE!!
            }
    }

}