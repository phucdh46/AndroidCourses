package com.jraska.rx.codelab

import com.jraska.rx.codelab.http.GitHubConverter
import com.jraska.rx.codelab.http.HttpModule
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

  companion object {
    private val LOGIN = "defunkt"
  }
}
