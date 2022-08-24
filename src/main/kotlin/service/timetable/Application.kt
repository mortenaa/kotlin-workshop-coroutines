package service.timetable

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {

    val departureDisplay = DepartureDisplay(TimetableService(), RealTimeService())

    while (true) {
        departureDisplay
        val time = measureTime {
            departureDisplay.update()
        }
        println("(update took $time")
       Thread.sleep(5000L)
    }
}
