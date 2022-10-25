package com.jraska.rx.codelab

import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Test
import java.util.concurrent.TimeUnit

class Task6SingleCompletableMaybe {

  @Test
  fun helloSingle() {
    // TODO: Create Single emitting one item and subscribe to it printing onSuccess value,
    // TODO: Convert Single to completable and print message about completion.
    var single = Single.just(1)
    single.subscribe(
      { v -> println(v) },
      { e -> println("Err: $e") }
    )
    //toComparable
    val comparable = single.ignoreElement()
    comparable.subscribe { println("completed") }
  }

  @Test
  fun maybe() {
    // TODO: Create a Single with one value to emit and convert it to maybe
    val single = Single.just("A")
    val maybe = single.toMaybe()
    maybe.subscribe(
      { v -> println(v) }
    )
  }

  @Test
  fun transformObservableToCompletable() {
    // TODO: Create Observable emitting values 1 .. 10 and make it completable (ignoreElements), subscribe and print
    val observables = Observable.range(1, 10)
    observables.ignoreElements().subscribe { println("Completed") }
  }

  @Test
  fun intervalRange_firstOrError_observableToSingle() {
    // TODO: Create Observable emitting 5 items each 10 ms (intervalRange)
    // TODO: Get first element (firstOrError)
    // TODO: Play around with skip operator, implement error handling for skip(5)

    val observables = Observable.intervalRange(1, 5, 10, 1, TimeUnit.MILLISECONDS)
    //observables.firstOrError().subscribe({v -> println(v) })
    observables.skip(6).firstOrError()
      .subscribe(
        { v -> println(v) },
        { e -> println("Error: $e") }
      )
  }

  @After
  fun after() {
    // to see easily time dependent operations, because we are in unit tests
    Thread.sleep(100)
  }
}
