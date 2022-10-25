package com.jraska.rx.codelab

import com.jraska.rx.codelab.http.GitHubConverter
import com.jraska.rx.codelab.http.HttpModule
import com.jraska.rx.codelab.http.UserCache
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class Task3Combining {
  private val gitHubApi = HttpModule.mockedGitHubApi()

  @Before
  fun setUp() {
    RxLogging.enableObservableSubscribeLogging()
  }

  @Test
  fun zipWith_userWithRepos() {
    // TODO: Use gitHubApi to get user with `LOGIN` and his repos and print them. Use `GitHubConverter::convert` as zipper
    gitHubApi.getUser(LOGIN)
      .zipWith(gitHubApi.getRepos(LOGIN), GitHubConverter::convert)
      .subscribe { println(it) }
  }

  @Test
  fun startWith_userInCache() {
    // TODO: Get user with `LOGIN` and startWith a `UserCache.getUserSync(LOGIN)`, Subscribe and print both values
    gitHubApi.getUser(LOGIN)
      .map { GitHubConverter.convert(it) }
      .startWith(UserCache.getUser(LOGIN))
      .subscribe { println(it) }
  }

  @Test
  fun merge_userInCache() {
    // TODO: Get user `UserCache.getUserSync(LOGIN)` and mergeWith user with `LOGIN`, Subscribe and print both values
    UserCache.getUser(LOGIN)
      .mergeWith(gitHubApi.getUser(LOGIN).map { GitHubConverter.convert(it) })
      .subscribe { println(it) }
  }

  @Test
  fun combineLatest_cachedUserWithRepos() {
    // TODO: Create observable of  `UserWithRepos` with `LOGIN` and use observable with cache from previous example - use Observable.combineLatest, GithubConverter::convert
    // TODO: Print the results - there should be two emission. Try to change order of passing into Observable.combineLatest - what happens?
    gitHubApi.getUser(LOGIN)
      .map { GitHubConverter.convert(it) }
      .startWith(UserCache.getUser(LOGIN))
      .combineLatest(
        gitHubApi.getRepos(LOGIN).map { GitHubConverter.convert(it) },
        GitHubConverter::convert
      )
      .subscribe { println(it) }

  }

  @Test
  fun testCombineLatest() {
    val observable1 = Observable.interval(400, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)

    Observable.combineLatest(
      observable1,
      observable2,
      object : BiFunction<Long, Long, String> {
        override fun apply(t1: Long, t2: Long): String {
          return "observable1 value: $t1 , observable2 value: $t2"
        }
      })
      .take(5)
      .subscribe { println(it) }

  }

  @Test
  fun testSwitchOnNext() {
    val timeIntervals = Observable.interval(1, TimeUnit.SECONDS)
      .map { ticks ->
        Observable.interval(100, TimeUnit.MILLISECONDS)
          .map { innerInterval -> "outer: $ticks - inner: $innerInterval" }
      }

    Observable.switchOnNext(timeIntervals)
      .subscribe { println(it) }

  }

  @Test
  fun testSwitchOnNext2() {
    Observable.switchOnNext(
      Observable.interval(100, TimeUnit.MILLISECONDS)
        .map { i: Long? ->
          Observable.interval(30, TimeUnit.MILLISECONDS)
            .map { i2: Long? -> i }
        }
    )
      .take(9)
      .subscribe { x: Long? -> println(x) }

    //0 0 0 1 1 1 2 2 2

  }

  companion object {
    private const val LOGIN = "defunkt"
  }
}
