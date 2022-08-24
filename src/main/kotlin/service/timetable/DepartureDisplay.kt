package service.timetable

import org.fusesource.jansi.Ansi.ansi

class DepartureDisplay(val timetableService: TimetableService, val realTimeService: RealTimeService) {

    fun update() {
        val header = StringBuilder()
        header.append(ansi().eraseScreen())
        header.append("                  DEPARTURES\n\n")
        header.append("==================================================\n")
        header.append("  #  To                   Platform Departure\n")
        header.append("--------------------------------------------------\n")
        print(header)
        val table = StringBuilder()
        timetableService.departures(10).forEach {
            it.apply {
                val liveTiming = realTimeService.liveDepartureTime(id)
                val timeString = if (liveTiming != null) {
                    "@|red $liveTiming (delayed)|@"
                } else {
                    "@|green $time|@"
                }
                table.append(ansi().render(String.format("%4s %-20s %5s    %-15s\n", line, destination, platform, timeString)))
            }
        }
        table.append("--------------------------------------------------\n")
        print(table)
    }

}
