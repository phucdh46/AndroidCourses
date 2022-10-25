package com.jraska.rx.codelab

import hu.akarnokd.rxjava.interop.RxJavaInterop
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class Task11OtherLibrariesInteroperability {

  // TODO: Uncomment Gradle dependencies for this test

  @Test
  fun givenRxJava1and2() {
    // TODO: Create RxJava 2 observable and convert it to RxJava 1 osbervable and vice versa
    val originV2 = Observable.just(1, 2, 3)

    val observableV1 =
      RxJavaInterop.toV1Observable(originV2.toFlowable(BackpressureStrategy.BUFFER))
    observableV1.subscribe { println(it) }
    val observableV2 = RxJavaInterop.toV2Observable(observableV1)
    observableV2.subscribe { println(it) }

    // TODO: Create RxJava 2 PublishProcessor and convert it to RxJava 1 Subject and back again

    val publishProcessV2 = PublishProcessor.create<String>()
    publishProcessV2.onNext("1")

    val publishProcessorV1 = RxJavaInterop.toV1Subject(publishProcessV2)
    val publishProcessorBackV2 = RxJavaInterop.toV2Subject(publishProcessorV1)
    publishProcessorBackV2.subscribe(::println)

  }

  @Test
  fun reactiveStreams_reactor_rxjava() {
    // TODO: Create RxJava 2 Observable and convert it to Reactor Flux, subscribe
    val observable = Observable.just(1, 2, 3)
    val flux = Flux.from(observable.toFlowable(BackpressureStrategy.BUFFER))
    flux.subscribe(::println)
    // TODO: Create RxJava 2 Single and convert it to Reactor Mono, subscribe
    val single = Single.just(1)
    val mono = Mono.from(single.toFlowable())
    mono.subscribe(::println)
  }
}
