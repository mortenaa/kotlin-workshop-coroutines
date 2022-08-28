package service.timetable

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DepartureFetcherTest {

    @Test
    @ExperimentalCoroutinesApi
    fun fetchDparturesAndLiveTimes() {

        val timeTableService = TimetableService()
        val realTimeService = RealTimeService()

        val departureFetcher = DepartureFetcher(timeTableService, realTimeService)
        runTest {
            val departures = departureFetcher.fetchDeparturesAndLiveTimes(10)
            assertEquals(10, departures.size)
        }

    }
}