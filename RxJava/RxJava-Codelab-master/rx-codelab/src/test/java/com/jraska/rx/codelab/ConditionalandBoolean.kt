package com.jraska.rx.codelab

import io.reactivex.Observable
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

class ConditionalandBoolean {

  //https://github.com/ReactiveX/RxJava/wiki/Conditional-and-Boolean-Operators#all
  @Test
  fun testAmb() {
    var s1 = Observable.range(1, 5)
    var s2 = Observable.range(6, 5)
    Observable.amb((Arrays.asList(s2, s1)))
      .subscribe { println(it) }
  }

  @Test
  fun testSkipUtilWhile() {
    var ob1 = Observable.range(1, 10).doOnNext { next -> Thread.sleep(1000) }

    ob1.skipUntil(Observable.timer(3, TimeUnit.SECONDS))
      .subscribe { println(it) }

    ob1.skipWhile { next -> next < 6 }
      .subscribe { println(it) }
  }

  @Test
  fun testTakeUntilWhile() {
    var ob1 = Observable.range(1, 10)

    ob1.takeUntil { v -> v > 5 }
      .subscribe { println(it) }

    ob1.takeWhile { v -> v < 5 }
      .subscribe { println(it) }

  }

  @Test
  fun testContains() {
    Observable.range(1, 10).doOnNext {
      println(it)
    }.contains(4)
  }
}
