package com.example.materialme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SportViewModel(app: Application) : AndroidViewModel(app) {
    private var list = arrayListOf<Sport>()

    var _sport = MutableLiveData<ArrayList<Sport>>()
    val sports: LiveData<ArrayList<Sport>>
        get() = _sport

    init {
        list = Data().initialzeData(app)
        _sport.value = list
    }

    fun removeSport(id: Int) {
        list.removeAt(id)
        _sport.value = list
    }

    fun resetSports(app: Application) {
        _sport.value = Data().initialzeData(app)
    }

    val _selectedSport = MutableLiveData<Sport>()
    val selectedSport: LiveData<Sport>
        get() = _selectedSport

    fun sendSport(sport: Sport) {
        _selectedSport.value = sport
    }
}