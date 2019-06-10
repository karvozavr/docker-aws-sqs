package ru.spb.hse.karvozavr.increment

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.*

class QueueManager {
    private val sqs: AmazonSQS = AmazonSQSClientBuilder.standard()
        .withEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration(
                "http://localstack:4576",
                "eu-central-1"
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
            if (!(e.message.equals("QueueAlreadyExists") || e.message.equals("QueueNameExists"))) {
                println(e.message)
            }
        }
    }

    fun deleteMessage(queueUrl: String, message: Message) {
        sqs.deleteMessage(queueUrl, message.receiptHandle)
    }

    fun receiveMessage(queueUrl: String): List<Message> =
        sqs.receiveMessage(queueUrl).messages

    fun sendMessage(queueUrl: String, message: String): SendMessageResult =
        sqs.sendMessage(
            SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
        )
}