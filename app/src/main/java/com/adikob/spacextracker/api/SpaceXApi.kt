package com.adikob.spacextracker.api

import com.adikob.spacextracker.models.LaunchInfo
import retrofit2.http.GET

interface SpaceXApi {

    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/launches/"
    }

    @GET(".")
    suspend fun getLaunches() : List<LaunchInfo>
}