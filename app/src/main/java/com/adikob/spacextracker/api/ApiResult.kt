package com.adikob.spacextracker.api

import com.adikob.spacextracker.models.LaunchInfo

class Result(val state: LoadingState, val launchInfoList: List<LaunchInfo> = ArrayList(0), val exception: NetworkException? = null) {

}

enum class LoadingState{
    LOADING,
    ERROR,
    DONE,
}

data class NetworkException(val message: String)