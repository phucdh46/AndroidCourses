package com.jraska.rx.codelab

import com.jraska.rx.codelab.http.GitHubConverter
import com.jraska.rx.codelab.http.HttpModule
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observables.GroupedObservable
import org.junit.Before
import org.junit.Test


class Task2Transformations {
  private val gitHubApi = HttpModule.mockedGitHubApi()

  @Before
  fun setUp() {
    RxLogging.enableObservableSubscribeLogging()
  }

  @Test
  fun map_convertUserDto() {
    // TODO: Use gitHubApi to get and print string representation of user with `LOGIN`. Use `User` and `GitHubConverter`
    gitHubApi.getUser(LOGIN)
      .map { GitHubConverter.convert(it) }
      .subscribe { println(it) }
  }

  @Test
  fun flatMap_getFirstUserDetailAfterGettingList() {
    // TODO:  Use gitHubApi to first get list of users and subsequently get its first user by other request
    gitHubApi.getFirstUsers()
      .flatMap { gitHubUsers -> gitHubApi.getUser(gitHubUsers[0].login) }
      .map { GitHubConverter.convert(it) }
      .subscribe { println(it) }

  }

  @Test
  fun replayAutoConnect_oneRequestForTwoSubscriptions() {
    // TODO: Get again the user with `LOGIN`, but subscribe twice and print only with 1 network request
    val loginObservable = gitHubApi.getUser(LOGIN)
      .map { GitHubConverter.convert(it) }
      .publish().autoConnect()
    loginObservable.subscribe { println(it) }
    loginObservable.subscribe { println(it) }


  }

  @Test
  fun testScan() {
    val observable = Observable.just(1, 2, 3, 4)
    observable.scan { x, y -> x + y }
      .subscribe(
        { v -> println(v) },
        { e -> println(e) },
        { println("Completed") }
      )
  }


  @Test
  fun testMap() {
    val observable = Observable.just(1, 2, 3, 4)
    observable
      .map { it -> it * it }
      .subscribe(
        { v -> println(v) },
        { e -> println(e) },
        { println("Completed") }
      )
  }

  @Test
  fun testFlatMap() {
    val observable = Observable.just(1, 2, 3, 4)
    observable
      //.flatMap { add(it) }\
      .flatMap { Observable.just(it).map { it * 3 } }
      .subscribe(
        { v -> println(v) },
        { e -> println(e) },
        { println("Completed") }
      )
  }

  fun add(num: Int): Observable<Int> {
    return object : Observable<Int>() {
      override fun subscribeActual(observer: Observer<in Int>?) {
        observer?.onNext(num * 2)
        observer?.onComplete()
      }

    }
  }

  @Test
  fun testBuffer() {
    val observable = Observable.just(1, 2, 3, 4)
    observable
      .buffer(3)
      .subscribe(
        { v -> println(v) },
        { e -> println(e) },
        { println("Completed") }
      )
  }

  @Test
  fun testWindow() {
    val observable = Observable.just(1, 2, 3, 4)
    observable
      .window(1)
      .subscribe(
        { v -> v.subscribe { println(it) } },
        { e -> println(e) },
        { println("Completed") }
      )
  }

  @Test
  fun testGroupBy() {
    val observable = Observable.just(1, 2, 3, 4, 9, 6)
    observable
      .groupBy { n -> if (n % 3 == 0) "A" else "B" }
      .subscribe(object : Observer<GroupedObservable<String, Int>> {
        override fun onSubscribe(d: Disposable) {
          println("onSubscribe")
        }

        override fun onNext(t: GroupedObservable<String, Int>) {
          if (t.key == "A") {
            t.subscribe { println(it) }
          }
        }

        override fun onError(e: Throwable) {
          println(e)
        }

        override fun onComplete() {
          println("onComplete")
        }

      })
  }

  @Test
  fun testGroupBy2() {
    val values = Observable.just(2)

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
  fun testUsing() {
    /*val values = Observable.using<Char?, String>(
      {
        val resource = "MyResource"
        println("Leased: $resource")
        resource
      },
      { resource: String ->
        Observable.create { o: ObservableEmitter<Char?> ->
          for (c in resource.toCharArray()) o.onNext(c!!)
         // o.onCompleted()
        }
      }
    ) { resource: String -> println("Disposed: $resource") }

    values
      .subscribe(
        { v: Char? -> println(v) }
      ) { e: Throwable? -> println(e) }*/

    val words = Observable.just(
      "First",
      "Second",
      "Third",
      "Fourth",
      "Fifth",
      "Sixth"
    )

    Observable.concat(words.groupBy { v -> v.get(0) })
      .subscribe(System.out::println)
  }

  companion object {
    private val LOGIN = "defunkt"
  }
}
