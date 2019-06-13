package ru.spb.hse.karvozavr.increment

import com.amazonaws.services.sqs.model.Message

class IncrementService(private val queueA: String, private val queueB: String) {

    private val queueAUrl: String
    private val queueBUrl: String
    private val sqsManager = QueueManager

    init {
        queueAUrl = sqsManager.getOrCreateQueue(queueA)
        queueBUrl = sqsManager.getOrCreateQueue(queueB)
    }

    fun start() {
        initialiseQueue()
        while (true) {
            processMessages()
        }
    }

    private fun initialiseQueue() {
        sqsManager.sendMessage(queueBUrl, "0")
    }

    private fun processMessages() {
        val messages: List<Message> = sqsManager.receiveMessage(queueAUrl)
        messages.forEach {
            println("Received message: $it.")
            incrementAndSend(it.body.toInt())
            sqsManager.deleteMessage(queueAUrl, it)
        }
    }

    private fun increment(x: Int): Int {
        return x + 1
    }

    private fun incrementAndSend(value: Int) {
        sqsManager.sendMessage(queueBUrl, increment(value).toString())
    }
}
