package com.jraska.rx.codelab

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import java.lang.System.err
import java.util.concurrent.TimeUnit


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

  @Test
  fun sample() {
    val values = Observable.range(1, 3)

    values
      .flatMap { i: Int? ->
        Observable.range(
          0,
          i!!
        )
      }
      .subscribe { println(it) }

  }

  @Test
  fun testCreate() {
    getObservable(listOf("A", "B", "A")).subscribe(
      { v -> println(v) },
      { e -> println(e) },
      { println("Completed") }
    )

  }

  fun getObservable(list: List<String>) =
    Observable.create<String> { emmiter ->
      list.forEach {
        if (it.equals("A")) emmiter.onNext(it)
        //
      }
      emmiter.onComplete()
    }

  @Test
  fun testInterval() {
    Observable.interval(1, TimeUnit.SECONDS).subscribe { time ->
      run {
        if ((time % 2).equals(0)) {
          println("a")
        } else println("b")

      }
    }

    /* Observable
       .interval(250,TimeUnit.MILLISECONDS)
       .map { i -> "A" }
       .take(10)
       .subscribe { println(it) }*/

    /* Observable.intervalRange(10L,5L,0L,1L,TimeUnit.SECONDS)
       .subscribe { println(it) }*/

    /* Observable.merge(
       Observable.interval(250, TimeUnit.MILLISECONDS).map { i -> "Apple" },
       Observable.interval(150, TimeUnit.MILLISECONDS).map { i -> "Orange" })
       .take(10)
       .subscribe{ v -> println("Received: $v") }*/
  }

  @Test
  fun testRepeat() {
    Observable.just(1, 2, 3, 4)
      .repeat(3)
      .subscribe { println(it) }
  }

  @Test
  fun testTImer() {
    Observable.timer(5, TimeUnit.SECONDS)
      .flatMap {
        return@flatMap Observable.create<String> { shooter ->
          shooter.onNext("GeeksforGeeks")
          shooter.onComplete()
          // Action to be done when completed 10 secs
        }
      }
      .subscribeOn(Schedulers.io())
      //.observeOn(AndroidSche)
      .subscribe {
      }


  }


  @Test
  fun test() {
    val values = Observable.create<Int> { o ->
      run {
        o.onNext(1)
        o.onNext(2)
        o.onNext(3)
        o.onComplete()
      }
    }

    val o = Observable.just(1, 2, 3)
    o.count()
      .subscribe(
        { v -> println(v) },
        { e -> println(e) }


      )


    values.all { i -> i > 0 }
      .subscribe({ v -> println(v) },
        { e -> println(e) }
      )
  }


  companion object {
    fun isOdd(value: Int): Boolean {
      return value % 2 == 1
    }
  }
}
