package com.e.golgirview.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.e.golgirview.model.ItemModel
import com.e.golgirview.repo.MainRepository
import com.e.golgirview.session.SessionManager
import com.e.golgirview.ui.BaseViewModel
import com.e.golgirview.ui.DataState
import com.e.golgirview.ui.main.state.LoginFields
import com.e.golgirview.ui.main.state.MainStateEvent
import com.e.golgirview.ui.main.state.MainViewState
import com.e.golgirview.util.AbsentLiveData
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val repository: MainRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : BaseViewModel<MainStateEvent, MainViewState>() {



    override fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        when (stateEvent) {

            is MainStateEvent.GetItemEvent -> {
                return repository.getData()
            }

            is MainStateEvent.None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setItemData(item: ItemModel) {
        val update = getCurrentViewStateOrNew()
        update.itemModel = item
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields){
        val update = getCurrentViewStateOrNew()
        if (update.loginFields == loginFields){
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }


    fun getDummyList(): List<ItemModel> {
        var dummyList = ArrayList<ItemModel>(10)
        return dummyList
    }

    fun getUserName():String{
        getCurrentViewStateOrNew()?.let {
            it.loginFields?.let{loginfields->
                return loginfields.login_name
            }
        }
    }

    fun getItemData():ItemModel{
        getCurrentViewStateOrNew()?.let {
            it.itemModel?.let{item->
                return item
            }
        }
        return ItemModel()
    }

    fun cancelActiveJobs() {
        repository.cancelActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(MainStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

    override fun initNewViewState(): MainViewState {
        return MainViewState()
    }

}













