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

    val x = async {
        var y = async { delay(2000L) }
        y.invokeOnCompletion { println("completed because $it") }
        y.await()
    }
    x.cancel()
    x.join()

}