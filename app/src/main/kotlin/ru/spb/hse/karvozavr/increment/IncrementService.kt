package ru.spb.hse.karvozavr.increment

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.AmazonSQSException
import com.amazonaws.services.sqs.model.CreateQueueRequest

class IncrementService(private val queueA: String, private val queueB: String) {

    private val queueAUrl: String
    private val queueBUrl: String
    private val sqsManager = QueueManager()

    init {
        queueAUrl = sqsManager.getOrCreateQueue(queueA)
        queueBUrl = sqsManager.getOrCreateQueue(queueB)
    }

    fun run() {

    }

    fun subscribeForQueues() {

    }
}