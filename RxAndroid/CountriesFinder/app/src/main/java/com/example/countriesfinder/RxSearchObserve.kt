package com.example.countriesfinder

import android.widget.SearchView
import io.reactivex.rxjava3.core.Observable

object RxSearchObserve {
    fun fromView(searchView: SearchView): Observable<String>{
        return Observable.create { emmiter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    emmiter.onNext(query!!)
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    emmiter.onNext(query!!)
                    return true
                }

            })
        }
    }
}