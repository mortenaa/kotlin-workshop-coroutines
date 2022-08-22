package samples.coroutines

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.*

suspend fun main() = coroutineScope {

    /**
     * Launch a coroutine that produces a value
     *
     * async returns a Deferred instance, which is a subclass of Job
     * deferred.await() will wait for the coroutine to produce a value.
     *
     * async like launch is an extension function on CoroutineScope.
     *
     */
    val client = HttpClient(CIO)
    client.get("")
    val result = async {
        println("Coroutine started")
        delay(1000L)
        println("Coroutine done")
        42
    }
    println("Coroutine launched")
    println("Done. Result=${ result.await() }")
}