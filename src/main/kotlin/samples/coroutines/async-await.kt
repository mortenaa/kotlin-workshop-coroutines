package samples.coroutines

import kotlinx.coroutines.*

suspend fun main() = coroutineScope {

    /**
     * Launch a coroutine that produces a value
     *
     * async returns a Defered instance, which is a subclass of Job
     * defered.await() will wait for the coroutine to produce a value.
     *
     * async like launch is an extension function on CoroutineScope.
     *
     */

    val result = async {
        println("Coroutine started")
        delay(1000L)
        println("Coroutine done")
        42
    }
    println("Coroutine launched")
    println("Done. Result=${result.await()}")
}