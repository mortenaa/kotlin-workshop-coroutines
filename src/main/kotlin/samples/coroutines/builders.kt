package samples.coroutines

import kotlinx.coroutines.*

fun main() {
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

    //coroutineScope {  }
}

