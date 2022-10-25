package com.jraska.rx.codelab

import hu.akarnokd.rxjava2.math.MathObservable
import io.reactivex.Observable
import org.junit.Test


class MathematicalandAggregate {
  @Test
  fun testMathAndAggregate() {
    //MathObservable.max(observable).subscribe { v -> println(v) }
    ///observable.subscribe { println(it) }
    // MathObservable.max(observable)
    val numbers = Observable.just(4, 9, 5)
    val numbers2 = Observable.just(4, 9, 5)
    //max
    MathObservable.max(numbers).subscribe { println(it) }
    //min
    MathObservable.min(numbers).subscribe { println(it) }
    //average
    MathObservable.averageDouble(numbers).subscribe { println(it) }
    //sum
    MathObservable.sumInt(numbers).subscribe { println(it) }
    //count
    numbers.count().subscribe { v -> println(v) }
    //reduce
    numbers.reduce { x, y -> x + y }.subscribe(::println)

  }
}
