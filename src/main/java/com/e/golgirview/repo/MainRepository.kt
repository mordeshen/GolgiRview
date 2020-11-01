package com.e.golgirview.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.e.golgirview.api.ApiService
import com.e.golgirview.api.response.ItemResponse
import com.e.golgirview.ui.main.state.MainViewState
import com.e.golgirview.model.ItemModel
import com.e.golgirview.persistence.MainDao
import com.e.golgirview.session.SessionManager
import com.e.golgirview.ui.DataState
import com.e.golgirview.util.ApiSuccessResponse
import com.e.golgirview.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val apiService: ApiService,
    val mainDao: MainDao,
    val sessionManager: SessionManager
) : JobManager("MainRepository") {

    private  val TAG = "MainRepository"
    fun getData(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<ItemResponse>, List<ItemModel>, MainViewState>(
            sessionManager.isConnectedToInternet(),
            true,
            true,
            true
        ) {

            override fun createCall(): LiveData<GenericApiResponse<List<ItemResponse>>> {
                return apiService.getItemFromApi()
            }

            override fun setJob(job: Job) {
                addJob("getData",job)
            }


            override fun loadFromCache(): LiveData<MainViewState> {

                return mainDao.getAll()
                    .switchMap {
                    Log.e(TAG, "loadFromCache: $it" )
                    object : LiveData<MainViewState>() {
                        override fun onActive() {
                            super.onActive()
                            value = MainViewState(
                                itemModel = it.first()
                            )
                        }
                    }
                }
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(viewState, null))
                        Log.e(TAG, "createCacheRequestAndReturn: $viewState" )
                    }

                }

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<List<ItemResponse>>) {
                Log.e(TAG, "handleApiSuccessResponse: ${response.body.toString()}")

                var tempArray:ArrayList<ItemModel> = ArrayList()
                for (item in response.body){
                    tempArray.add(
                        ItemModel(
                            pk = 0,
                            question = item.question,
                            answer = item.answer
                        )
                    )
                }


                Log.e(TAG, "handleApiSuccessResponse@@@@@@: $tempArray" )
                updateLocalDb(tempArray)
                createCacheRequestAndReturn()
            }

            override suspend fun updateLocalDb(cacheObject: List<ItemModel>?) {
                Log.e(TAG, "updateLocalDb: $cacheObject" )
                if (cacheObject != null) {
                    withContext(Dispatchers.IO) {

                            try {
                                launch {
                                    Log.d(TAG, "updateLocalDb: inserting item: $cacheObject")
                                    mainDao.insert(cacheObject.first())
                                }

                            } catch (e: Exception) {
                                Log.e(
                                    TAG, "updateLocalDb: error updating cache" +
                                            "on item with name: ${cacheObject.first().pk}"
                                )
                            }

                    }
                }
            }

        }.asLiveData()
    }
}