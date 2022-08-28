package service.timetable

import kotlinx.coroutines.*
import java.lang.Exception
import java.util.concurrent.TimeoutException

class DepartureFetcher(val timetableService: TimetableService, val realTimeService: RealTimeService) {

    fun fetchDparturesAndLiveTimesOld(max: Int): List<DepartureWithLiveTime> {
        return timetableService.departures(max).map {
            val liveTime = try {
                realTimeService.liveDepartureTime(it.id)
            } catch (e: Exception) {
                null
            }
            DepartureWithLiveTime(it, liveTime)
        }
    }


    suspend fun fetchDeparturesAndLiveTimes(max: Int): List<DepartureWithLiveTime> = coroutineScope {
        val departures = timetableService.departures(max)
        val deferred = departures.map {
            async {
                try {
                    realTimeService.liveDepartureTime(it.id)
                } catch (e: TimeoutException) { null }
            }
        }
        val times = awaitAll(*deferred.toTypedArray())
        departures.zip(times) { dep, time ->
            DepartureWithLiveTime(dep, time)
        }
    }

    /**
     * 1. make fetch function suspend
     * 2. make display function suspend
     * 3. make main runBlocking
     * 4. split fetching into
     *      1. fetch timetable
     *      2. fetch real times each in a separate coroutine
     *      3. await real times
     *      4. merge table with times
     */

}