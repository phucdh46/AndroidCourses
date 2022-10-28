package com.example.rxkotlinexample

import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class KotlinTests {
    @Mock var a: ScriptAssertion = uninitialized()

    @Before fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Suppress("BASE_WITH_NULLABLE_UPPER_BOUND")
    fun <T> received() = { result: T -> a.received(result) }

    interface ScriptAssertion {
        fun error(e: Throwable?)

        fun received(e: Any?)
    }

    private fun <T> uninitialized() = null as T
}