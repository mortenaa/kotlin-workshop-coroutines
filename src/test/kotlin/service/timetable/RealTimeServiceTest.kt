package service.timetable

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RealTimeServiceTest {

    @Test
    fun liveDepartureTime() {

        val realTimeService = RealTimeService()
        val time = realTimeService.liveDepartureTime("7b5d0da5-4f5f-4145-afe7-0e4979023580")

        assertEquals(DepartureTime(Hours(10), Minutes(2)), time)
    }

    @Test
    fun test() {
        val realTimeService = RealTimeService()
        val time = realTimeService.liveDepartureTime("c7a6ad45-6c8e-4d74-9f9b-9ec19ac6ceee")

        assertNull(time)

    }
}