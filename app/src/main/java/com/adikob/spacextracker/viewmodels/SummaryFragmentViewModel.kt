package com.adikob.spacextracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adikob.spacextracker.api.LoadingState
import com.adikob.spacextracker.api.NetworkException
import com.adikob.spacextracker.api.Repository
import com.adikob.spacextracker.api.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SummaryFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){
    private var launchInfoData: MutableLiveData<Result>? = null

    fun getData() : LiveData<Result>{
        if (launchInfoData == null){
            launchInfoData = MutableLiveData()
            loadData()
        }
        return launchInfoData!!
    }

    fun refreshData(){
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            val result = try {
                var apiResult = repository.loadData()
                apiResult = apiResult.sortedByDescending { it.launchDate }
                Result(state = LoadingState.DONE, launchInfoList = apiResult)
            }catch (exception: IOException) {
                Result(state = LoadingState.ERROR, exception = NetworkException(exception.message ?: "Network error occurred"))
            } catch (exception: HttpException) {
                Result(state = LoadingState.ERROR, exception = NetworkException(exception.message ?: "Network error occurred"))
            }
            launchInfoData?.value = result
        }
    }
}