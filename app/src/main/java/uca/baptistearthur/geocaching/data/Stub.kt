package uca.baptistearthur.geocaching.data

import org.osmdroid.util.GeoPoint
import uca.baptistearthur.geocaching.model.Address
import uca.baptistearthur.geocaching.model.Place
import uca.baptistearthur.geocaching.model.RoadTripEntity
import java.util.Date

class Stub {
    fun load(): MutableList<RoadTripEntity> {
        val list = listOf(
            RoadTripEntity(
                1,
                "France",
                Date(),
                listOf(Place(49.3, 49.3)).toMutableList()
            ),
            RoadTripEntity(
                2,
                "Italie",
                Date(),
                listOf(Place(48.866667, 2.34533), Place(98.866667, 2.333333)).toMutableList()
            ),
            RoadTripEntity(
                3,
                "Danemark",
                Date(),
                listOf(Place(48.866667, 2.333333), Place(48.866667, 2.333333)).toMutableList()
            ),
            RoadTripEntity(
                4,
                "Islande",
                Date(),
                listOf(Place(48.866667, 2.333333), Place(48.866667, 2.333333), Place(48.866667, 2.333333)).toMutableList()
            ),
        )
        return list.toMutableList()
    }

}