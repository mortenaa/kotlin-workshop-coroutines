package service.timetable

data class DepartureWithLiveTime(
    val departure: Departure,
    val liveTime: DepartureTime?)