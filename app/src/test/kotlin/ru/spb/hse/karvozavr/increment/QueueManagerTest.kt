package ru.spb.hse.karvozavr.increment

import org.junit.Test

class QueueManagerTest {

    @Test
    fun checkQueueExists() {
        val manager = QueueManager
        if (!manager.checkQueueExists("superQueue")) {
            manager.createQueue("superQueue")
        }
    }
}