package exercises.coroutines

import kotlinx.coroutines.*
import kotlin.random.Random

fun main(): Unit {
    Exercises().unstableCaller()
}

class Exercises {

    fun sumListsOld(lists: List<List<Int>>): Int {
        return lists.map { it.sum() }.sum()
    }

    suspend fun sumLists(lists: List<List<Int>>): Int = coroutineScope {
        val deferred = lists.map {
            async { it.sum() }
        }
        val sums = deferred.awaitAll()
        sums.sum()
    }

    fun unstable(number: Int): Int {
        Thread.sleep(100L)
        if (Random.nextBoolean()) {
            return (0..number).sum()
        } else {
            throw IllegalStateException()
        }
    }

    fun unstableCaller() = runBlocking {
        supervisorScope {
            val deferred = (1 .. 42).map {
                async(start = CoroutineStart.LAZY) {
                    unstable(42)
                }
            }
            val result = deferred.awaitAll().sum()
            println(result)
        }
    }

    /*
        application:

        try to set some breakpoints in coroutines and start debug run
        make sure breakpoint is set to stop all threads
        look around in the debuger to se active corutines

        Try to add the DebugProbe agent to the application, and dump coroutines
        with DebugProbe.dumpCoroutines()
        Remember to call DebugProbe.install()
        make sure to do it in a place where the coroutines are active

     */


}