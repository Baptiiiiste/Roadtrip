package uca.baptistearthur.geocaching.data

import androidx.annotation.RequiresPermission.Read
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uca.baptistearthur.geocaching.model.RoadTripEntity

@androidx.room.Dao
interface RoadTripDAO {

    @Insert
    suspend fun insertRoadTrip(r: RoadTripEntity)

    @Delete
    suspend fun deleteRoadTrip(r: RoadTripEntity)

    @Query("SELECT * FROM Roadtrip ORDER BY date")
    fun getAllRoadTrips(): Flow<MutableList<RoadTripEntity>>

    @Query("SELECT * FROM Roadtrip WHERE id = :id")
    fun getRoadTripById(id: Int): Flow<RoadTripEntity>
}
