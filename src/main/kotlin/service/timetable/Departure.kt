package service.timetable

data class Departure(
    val id: String,
    val line: String,
    val destination: String,
    val time: DepartureTime,
    val platform: String
)

data class DepartureTime(val hours: Hours, val minutes: Minutes) {
    init {
        require(hours.value < 24 || minutes.value == 0.toByte())
    }
    override fun toString() = "$hours:$minutes"
}

@JvmInline
value class Hours(val value: Byte) {
    init {
        require(value in 0..24)
    }
    override fun toString() = String.format("%02d", value)
}

@JvmInline
value class Minutes(val value: Byte) {
    init {
        require(value in 0 .. 59)
    }
    override fun toString() = String.format("%02d", value)
}