package samples.coroutines.async

import kotlinx.coroutines.*

suspend fun main() = coroutineScope {

    /**
     * # Launch a coroutine that produces a value
     *
     * [async] returns a [Deferred] instance, which is a subclass of [Job]
     *
     * `deferred.await()` will wait for the coroutine to produce a value.
     *
     * async like launch is an extension function on the [CoroutineScope] interface.
     *
     */

    val result: Deferred<Int> = async {
        println("Coroutine started")
        delay(1000L)
        println("Coroutine done")
        42
    }
    println("Coroutine launched")
    println("Done. Result=${ result.await() }")

    val deferreds: List<Deferred<Int>> = (1..100).map {
        async {
            delay(1000)
            it
        }
    }
    val list: List<Int> = deferreds.awaitAll()
    println("Sum = ${list.sum()}")
}