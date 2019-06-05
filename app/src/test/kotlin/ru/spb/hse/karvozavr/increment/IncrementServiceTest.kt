package ru.spb.hse.karvozavr.increment

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*

class IncrementServiceTest {

    @Test
    fun testService() {
        val a = "a_test_q"
        val b = "b_test_q"
        Thread {
            val service = IncrementService(a, b)
            service.start()
        }.start()

        Thread {
            val service = IncrementService(b, a)
            service.start()
        }.start()

        Thread.sleep(10000)
    }
}