package samples.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    /**
     * Launch a coroutine.
     *
     * Notice the order of the printed output.
     *
     * launch is an extension function on CoroutineScope. Here the scope it set up by
     * runBlocking, and is available as `this`, thus we can call launch without explicitly
     * providing a coroutinescope.
     *
     */

    val job = launch {
        println("Coroutine started")
        delay(1000L)
        println("Coroutine done")
    }
    println("Coroutine launched")
}