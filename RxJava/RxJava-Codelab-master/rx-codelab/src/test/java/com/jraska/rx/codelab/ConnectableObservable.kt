package com.jraska.rx.codelab

import io.reactivex.Observable
import org.junit.Test
import java.util.concurrent.TimeUnit

class ConnectableObservable {
  @Test
  fun testPublish() {
    val firstMillion =
      Observable.range(1, 10).publish()

    firstMillion.subscribe(
      { next -> println("Subscriber #1: $next") },  // onNext
      { throwable -> println("Error: $throwable") })  // onError

    { println("Sequence #1 complete") } // onComplete


    firstMillion.subscribe(
      { next -> println("Subscriber #2: $next") },  // onNext
      { throwable -> println("Error: $throwable") })  // onError

    { println("Sequence #2 complete") } // onComplete


    firstMillion.connect()
  }

  @Test
  fun testRefCount() {
    val cold = Observable.interval(200, TimeUnit.MILLISECONDS).publish().refCount()
    var s1 = cold.subscribe { i: Long -> println("First: $i") }
    Thread.sleep(500)

    val s2 = cold.subscribe { i: Long -> println("Second: $i") }
    Thread.sleep(500)
    println("Unsubscribe second")
    s2.dispose()
    Thread.sleep(500)
    println("Unsubscribe first")
    s1.dispose()
    s1 = cold.subscribe { i: Long -> println("First: $i") }

    println("First connection again")
    Thread.sleep(1500)
  }

  @Test
  fun testReplay() {
    val cold = Observable.interval(200, TimeUnit.MILLISECONDS).replay()
    val s = cold.connect()

    println("Subscribe first")
    val s1 = cold.subscribe({ i -> println("First: $i") })
    Thread.sleep(700)
    println("Subscribe second")
    val s2 = cold.subscribe({ i -> println("Second: $i") })
    Thread.sleep(500)
  }
}
