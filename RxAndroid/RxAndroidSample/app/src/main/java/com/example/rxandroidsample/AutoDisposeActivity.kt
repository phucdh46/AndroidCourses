package com.example.rxandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.androidx.lifecycle.autoDispose
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class AutoDisposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_dispose)
        // With extension function that overloads LifecycleOwner
        Observable.interval(1, TimeUnit.SECONDS)
            .autoDispose(this)
            .subscribe()

        // With extension function that overloads LifecycleOwner and until Event
        Observable.interval(1, TimeUnit.SECONDS)
            .autoDispose(this, Lifecycle.Event.ON_DESTROY)
            .subscribe()

        // With extension function that overloads ScopeProvider
        Observable.interval(1, TimeUnit.SECONDS)
            .autoDispose(scope(Lifecycle.Event.ON_DESTROY))
            .subscribe()

        // With no extension function
        Observable.interval(1, TimeUnit.SECONDS)
            .autoDispose(AndroidLifecycleScopeProvider.from(this))
            .subscribe()
    }
}