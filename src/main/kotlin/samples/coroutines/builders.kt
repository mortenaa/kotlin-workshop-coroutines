package samples.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

suspend fun main() {

    coroutineContext.isActive
    runBlocking {
        delay(1000L)
        yield()
        val job = launch {
            delay(1000L)
        }
        job.join()
    }

    GlobalScope.launch {
        delay(1000L)
        println("done!")
    }
    Thread.sleep(2000L)

    coroutineScope {
        this.launch {
            delay(1000L)
            var x = this.coroutineContext
            println("$x")
        }.join()
    }
}

