package com.jraska.rx.codelab

import io.reactivex.plugins.RxJavaPlugins

object RxLogging {

  fun enableObservableSubscribeLogging() {
    RxJavaPlugins.setOnObservableSubscribe { observable, observer ->
      println(observable.javaClass.name + ".onSubscribe(" + observer.javaClass.simpleName + ")")
      return@setOnObservableSubscribe observer
    }
  }
}
