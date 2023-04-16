package com.adikob.spacextracker.api

import com.adikob.spacextracker.models.LaunchInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val spaceXApi: SpaceXApi) {

    suspend fun loadData(): List<LaunchInfo>{
        return withContext(Dispatchers.IO){
            spaceXApi.getLaunches()
        }
    }

}