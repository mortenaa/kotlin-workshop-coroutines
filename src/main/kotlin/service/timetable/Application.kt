package service.timetable

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlinx.coroutines.debug.DebugProbes

@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
fun main() = runBlocking {

    val departureDisplay = DepartureDisplay(TimetableService(), RealTimeService())

    while (true) {
        DebugProbes.install()
        val time = measureTime {
            departureDisplay.update()
            DebugProbes.dumpCoroutines()

        }
        println("(update took $time")
       Thread.sleep(5000L)
    }
}
