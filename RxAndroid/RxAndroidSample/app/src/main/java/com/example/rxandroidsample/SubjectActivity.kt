package com.example.rxandroidsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject

class SubjectActivity : AppCompatActivity() {
    companion object{
        const val TAG = "DHP"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        asyncSubject()
    }

    @SuppressLint("AutoDispose")
    fun asyncSubject(){
        val asyncSubject = AsyncSubject.create<Int>()
//        val asyncSubject = ReplaySubject.create<Int>()
//        val asyncSubject = BehaviorSubject.create<Int>()
//        val asyncSubject = PublishSubject.create<Int>()

        asyncSubject.subscribe(getFisrtAsyncSubject())

        asyncSubject.onNext(1)
        asyncSubject.onNext(2)
        asyncSubject.onNext(3)

        asyncSubject.subscribe(getSecondAsyncSubject())

        asyncSubject.onNext(4)
        asyncSubject.onComplete()

    }
    fun getFisrtAsyncSubject() = object : Observer<Int>{
        override fun onSubscribe(d: Disposable) {
            Log.d(TAG,"First onSubscribe : ${d.isDisposed}")
        }

        override fun onNext(t: Int) {
            Log.d(TAG, " First onNext value : $t")
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, " First onError : " + e.message)
        }

        override fun onComplete() {
            Log.d(TAG, " First onComplete")
        }
    }
    fun getSecondAsyncSubject() = object : Observer<Int>{
        override fun onSubscribe(d: Disposable) {
            Log.d(TAG,"Second onSubscribe : ${d.isDisposed}")
        }

        override fun onNext(t: Int) {
            Log.d(TAG, " Second onNext value : $t")
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, " Second onError : " + e.message)
        }

        override fun onComplete() {
            Log.d(TAG, " Second onComplete")
        }

    }
}