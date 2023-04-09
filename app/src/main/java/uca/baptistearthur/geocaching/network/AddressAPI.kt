package uca.baptistearthur.geocaching.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import uca.baptistearthur.geocaching.model.Address

interface AddressAPI {
    @GET("/v1/reverse")
    suspend fun getAddress(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Address
}