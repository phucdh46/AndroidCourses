package com.example.countriesfinder

import androidx.lifecycle.*
import com.example.countriesfinder.data.Country
import com.example.countriesfinder.data.CountryDao
import com.example.countriesfinder.data.Datasource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryViewModel(val countryDao: CountryDao) : ViewModel() {

    init {
        initializeData().subscribe()
    }

    val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message
    val countries = countryDao.getAll()

    fun initializeData(): Flowable<List<Country>> {
        return Maybe.fromAction<List<Country>> {
            val list = arrayListOf<Country>()
            Datasource.COUNTRIES.forEach { name ->
                list.add(Country(name = name))
            }
            countryDao.insertAll(list)
        }.toFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _message.value = "Initialze data success" }
            .doOnError { _message.value = "Error insert data: $it" }
    }

    fun initializeData1() {
        viewModelScope.launch(Dispatchers.IO) {
            countryDao.deleteAll()
            Datasource.COUNTRIES.forEach { name ->
                countryDao.insert(Country(name = name))
            }
        }
    }

    fun search(query: String): List<Country> {
        Thread.sleep(1000)
        return countryDao.search("%$query%")
    }


}

class CountryViewModelFactory(private val countryDao: CountryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountryViewModel(countryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}