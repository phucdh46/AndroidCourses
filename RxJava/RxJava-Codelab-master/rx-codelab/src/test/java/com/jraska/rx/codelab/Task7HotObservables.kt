package com.jraska.rx.codelab

import com.jraska.rx.codelab.http.HttpBinApi
import com.jraska.rx.codelab.http.HttpModule
import com.jraska.rx.codelab.http.RequestInfo
import com.jraska.rx.codelab.server.RxServer
import com.jraska.rx.codelab.server.RxServerFactory
import io.reactivex.Observable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class Task7HotObservables {

  private val rxServer: RxServer = RxServerFactory.create()
  private val httpBinApi: HttpBinApi = HttpModule.httpBinApi()

  @Before
  fun before() {
    RxLogging.enableObservableSubscribeLogging()
  }

  @Test
  fun coldObservable() {
    val getRequest = httpBinApi.getRequest()
      .subscribeOn(Schedulers.io())

    // TODO: Subscribe twice to getRequest and print its values, how many http requests it triggers?
    // TODO: Delay first subscription by 250 ms - delaySubscription()
    // TODO: Modify getRequest to be able to perform only one http request - share()

    val request = getRequest.delaySubscription(250, TimeUnit.MILLISECONDS).share()
    request.subscribe(
      { v -> println("1: $v") },
      { e -> println("Error: $e") },
      { println("Completed") }
    )
    request.subscribe(
      { v -> println("2: $v") },
      { e -> println("Error: $e") },
      { println("Completed") }
    )
  }

  @Test
  fun hotObservable() {
    // TODO: Subscribe twice to rxServer.debugLogsHot and print the logs
    // TODO: Delay first subscription by 250ms - delaySubscription(), how is this different than cold observable
    val debugLogs = rxServer.debugLogsHot().delaySubscription(250, TimeUnit.MILLISECONDS)
    debugLogs.subscribe { println("1: $it") }
    debugLogs.subscribe { println("2: $it") }
  }

  @Test
  fun createHotObservableThroughSubject() {
    val getRequest = httpBinApi.getRequest()

    // TODO: Create a PublishSubject<RequestInfo> and subscribe twice to it with printing the result
    // TODO: Subscribe to getRequest and publish its values to subject

    val subject: PublishSubject<RequestInfo> = PublishSubject.create()
    subject.subscribe { println(it) }
    subject.subscribe { println(it) }

    getRequest.subscribe(subject)
  }

  //Connect
  @Test
  fun testConnectableObserable() {
    val cold: ConnectableObservable<Long> =
      Observable.interval(200, TimeUnit.MILLISECONDS).publish()
    cold.connect()
    val s1 = cold.subscribe { v -> println("1: $v") }
    Thread.sleep(500)
    cold.subscribe { v -> println("2: $v") }

  }

  @After
  fun after() {
    Thread.sleep(500)
    HttpModule.awaitNetworkRequests()
  }
}
