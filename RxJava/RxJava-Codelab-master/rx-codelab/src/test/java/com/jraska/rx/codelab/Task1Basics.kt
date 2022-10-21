package com.jraska.rx.codelab

import io.reactivex.Observable
import org.junit.Test
import java.lang.System.err

class Task1Basics {
  @Test
  fun dummyObservable() {
    // TODO:  Create Observable with single String value, subscribe to it and print it to console (Observable.just)
    Observable.just("a").subscribe { println(it) }
  }

  @Test
  fun methodIntoObservable() {
    // TODO:  Create Observable getting current time, subscribe to it and print value to console (Observable.fromCallable)
    Observable.fromCallable {
      System.currentTimeMillis()
    }.subscribe { println(it) }
  }

  @Test
  fun deferOperator() {
    val observable = Observable.defer {
      val time = System.currentTimeMillis()
      Observable.just(time)
    }

    observable.subscribe { time: Long? -> println(time) }

    Thread.sleep(1000)

    observable.subscribe { time: Long? -> println(time) }
  }

  @Test
  fun helloOperator() {
    // TODO:  Create Observable with ints 1 .. 10 subscribe to it and print only odd values (Observable.range, observable.filter)
    Observable.range(1, 10)
      .filter { isOdd(it) }.subscribe { println(it) }
  }

  @Test
  fun receivingError() {
    // TODO:  Create Observable which emits an error and print the console (Observable.error), subscribe with onError handling
    Observable.error<Int>(RuntimeException("e")).subscribe({ println(it) }, { err.println(it) })

  }

  companion object {
    fun isOdd(value: Int): Boolean {
      return value % 2 == 1
    }
  }
}
