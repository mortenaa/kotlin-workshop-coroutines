package exercises.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import service.UserService

@ExperimentalCoroutinesApi
internal class CoroutineUserServiceTest {

    @Test
    fun getUserAndItems() = runTest {
        val coroutineUserService = CoroutineUserService(UserService())
        val result = coroutineUserService.getUserAndItems("101")
        assertEquals("Signy Evensen", result.first.name)
        assertEquals(1, result.second.size)
    }

    @Test
    fun getUserAndItemsCoroutine() = runTest {
        val coroutineUserService = CoroutineUserService(UserService())
        val result = coroutineUserService.getUserAndItemsCoroutine("101")
        assertEquals("Signy Evensen", result.first.name)
        assertEquals(1, result.second.size)
        assertEquals(2000, currentTime)
    }

    @Test
    fun getUserAndItemsConcurrent() = runTest {
        val coroutineUserService = CoroutineUserService(UserService())
        val result = coroutineUserService.getUserAndItemsConcurrent("101")
        assertEquals("Signy Evensen", result.first.name)
        assertEquals(1, result.second.size)
        assertEquals(1000, currentTime)
    }

    @Test
    fun getUsersAndItemsInBulk() = runTest {
        val coroutineUserService = CoroutineUserService(UserService())
        val result = coroutineUserService.getUsersAndItemsInBulk(listOf("101", "102", "103"))
        assertEquals(3, result.size)
        val user = result.first()
        assertEquals("Signy Evensen", user.first.name)
        assertEquals(1, user.second.size)
        assertEquals(1000, currentTime)
    }
}