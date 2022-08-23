package service.timetable

import org.fusesource.jansi.Ansi.ansi
import org.fusesource.jansi.AnsiConsole
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {

    val departureDisplay = DepartureDisplay(TimetableService(), RealTimeService())
    
    while (true) {
        val time = measureTime {
            val display = departureDisplay.update()
            println(display)
        }
        println("(update took $time")
       Thread.sleep(5000L)
    }
}
