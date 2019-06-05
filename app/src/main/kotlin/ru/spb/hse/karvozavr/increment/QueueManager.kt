package ru.spb.hse.karvozavr.increment

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.AmazonSQSException
import com.amazonaws.services.sqs.model.CreateQueueRequest
import com.amazonaws.services.sqs.model.QueueDoesNotExistException

class QueueManager {
    private val sqs: AmazonSQS = AmazonSQSClientBuilder.standard()
        .withEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration(
                "http://localhost:4576",
                "eu-west-1"
            )
        )
        .build()

    fun checkQueueExists(queueName: String): Boolean {
        return try {
            sqs.getQueueUrl(queueName)
            true
        } catch (e: QueueDoesNotExistException) {
            false
        }
    }

    fun getQueue(queueName: String): String = sqs.getQueueUrl(queueName).queueUrl

    fun getOrCreateQueue(queueName: String): String {
        if (!checkQueueExists(queueName))
            createQueue(queueName)
        return getQueue(queueName)
    }


    fun createQueue(queueName: String) {
        sqs.createQueue(queueName)
        val createQueueRequest: CreateQueueRequest =
            CreateQueueRequest(queueName)
                .addAttributesEntry("DelaySeconds", "2")
                .addAttributesEntry("MessageRetentionPeriod", "86400")
        try {
            sqs.createQueue(createQueueRequest)
        } catch (e: AmazonSQSException) {
            throw e
        }
    }
}