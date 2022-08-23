package service.timetable

class DepartureDisplay(val timetableService: TimetableService, val realTimeService: RealTimeService) {

    fun update(): String {
        val buffer = StringBuilder()
        buffer.append("                  DEPARTURES\n\n")
        buffer.append("==================================================\n")
        buffer.append("  #  To                   Platform Departure\n")
        buffer.append("--------------------------------------------------\n")

        timetableService.departures(10).forEach {
            it.apply {
                val liveTiming = realTimeService.liveDepartureTime(id)
                val timeString = if (liveTiming != null) {
                    "$liveTiming (delayed)"
                } else {
                    time
                }
                buffer.append(String.format("%4s %-20s %5s    %-15s\n", line, destination, platform, timeString))
            }
        }
        buffer.append("--------------------------------------------------\n")
        return buffer.toString()
    }

}
