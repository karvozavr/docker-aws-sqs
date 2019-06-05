package ru.spb.hse.karvozavr.increment

fun main(args: Array<String>) {
    if (args.size != 2) {
        error("Invalid arguments")
    }

    val queueA = args[0]
    val queueB = args[1]

    val service = IncrementService(queueA, queueB)
    service.start()
}