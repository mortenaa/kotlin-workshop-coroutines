package exercises.coroutines

import kotlinx.coroutines.*
import service.Product
import service.User
import service.UserService
import java.time.Instant
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

val userService = UserService()

fun main(): Unit = runBlocking {

    val coroutineUserService = CoroutineUserService(UserService())

    coroutineUserService.getUserAndItems("101")

//    coroutineUserService.getUserAndItemsCoroutine("102")
//    coroutineUserService.getUserAndItemsConcurrent("103")
//    coroutineUserService.getUsersAndItemsInBulk(listOf("101", "102", "103"))

}

/**
 * Exercise 1.
 *
 * The UserService emulates a blocking service
 * In this exercise try first to launch a coroutine that does
 * the service calls.
 *
 * Then try to speed up the calls by doing them concurrently.
 *
 */

class CoroutineUserService(val userService: UserService) {

    @OptIn(ExperimentalTime::class)
    suspend fun getUserAndItems(userId: String): Pair<User, List<Product>> {
        val result = measureTimedValue {
            val user = userService.getUser(userId)
            val items = userService.getProducts(userId)
            println("Got $items for $user")
            user to items
        }
        println("Done in ${result.duration.inWholeMilliseconds}")
        return result.value
    }

    suspend fun getUserAndItemsCoroutine(userId: String): Pair<User, List<Product>> = coroutineScope {
        TODO()
    }

    suspend fun getUserAndItemsConcurrent(userId: String): Pair<User, List<Product>> = coroutineScope {
        TODO()
    }

    suspend fun getUsersAndItemsInBulk(userIds: List<String>): List<Pair<User, List<Product>>> = coroutineScope {
        TODO()
    }
}