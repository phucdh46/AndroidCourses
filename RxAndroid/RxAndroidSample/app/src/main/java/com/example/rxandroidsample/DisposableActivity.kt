package com.example.rxandroidsample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class DisposableActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()
    companion object{
        const val TAG = "DHP"
    }
    @SuppressLint("AutoDispose")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disposable)

        disposable.add(
            Observable.just(1,2,3)
                .subscribeOn(Schedulers.io())
                .map { convertToString(it) }
                .flatMap {it->
                    Observable.just(it+"s").delay(3,TimeUnit.SECONDS)
                }

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {v->Log.d(TAG,"onNext: $v")},
                    {e->Log.e(TAG,"onError: ${e.message}")},
                    {Log.d(TAG,"onComplete")}
                )
        )
        Observable.just(1,2).firstElement()

    }           

    fun convertToString(i: Int) = (i.toString() + "T")

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

}