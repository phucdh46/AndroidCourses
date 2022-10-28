package com.example.rxandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import com.trello.rxlifecycle4.kotlin.bindUntilEvent
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class RxLifecycleActivity : RxAppCompatActivity() {
    val TAG = "DHP"
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_rx_lifecycle)
        Log.d(TAG,"onCreate()")

        // Specifically bind this until onPause()
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { Log.i(TAG, "Unsubscribing subscription from onCreate()") }
            .bindUntilEvent(this, ActivityEvent.PAUSE)
            .subscribe { num -> Log.i(TAG, "Started in onCreate(), running until onPause(): " + num) }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart()")

        // Using automatic unsubscription, this should determine that the correct time to
        // unsubscribe is onStop (the opposite of onStart).
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { Log.i(TAG, "Unsubscribing subscription from onStart()") }
            .bindToLifecycle(this)
            .subscribe { num -> Log.i(TAG, "Started in onStart(), running until in onStop(): " + num) }
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { Log.i(TAG, "Unsubscribing subscription from onResume()") }
            .bindUntilEvent(this, ActivityEvent.DESTROY)
            .subscribe { num -> Log.i(TAG, "Started in onResume(), running until in onDestroy(): " + num) }
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }

}