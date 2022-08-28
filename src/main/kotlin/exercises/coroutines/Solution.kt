package exercises.coroutines

import kotlinx.coroutines.*
import service.Product
import service.User
import service.UserService
import java.time.Instant


fun main(): Unit = runBlocking {

    val coroutineUserService = CoroutineUserService(UserService())

    coroutineUserService.getUserAndItems("101")

    coroutineUserService.getUserAndItemsCoroutine("102")

    coroutineUserService.getUserAndItemsConcurrent("103")

    coroutineUserService.getUsersAndItemsInBulk(listOf("101", "102", "103"))
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

class CoroutineUserServiceS(val userService: UserService) {

    suspend fun getUserAndItems(userId: String): Pair<User, List<Product>> {
        val start = Instant.now().toEpochMilli()
        val user = userService.getUser(userId)
        val items = userService.getProducts(userId)
        println("Got $items for $user")
        val end = Instant.now().toEpochMilli()
        println("Done in ${end - start} millis")
        return user to items
    }

    suspend fun getUserAndItemsCoroutine(userId: String): Pair<User, List<Product>> = coroutineScope {
        val start = Instant.now().toEpochMilli()
        val result = async {
            val user = userService.getUser(userId)
            val items = userService.getProducts(userId)
            println("Got $items for $user")
            user to items
        }.await()
        val end = Instant.now().toEpochMilli()
        println("Done in ${end - start} millis")
        result
    }

    suspend fun getUserAndItemsConcurrent(userId: String): Pair<User, List<Product>> = coroutineScope {
        val start = Instant.now().toEpochMilli()
        val result = async {
            val user = async { userService.getUser(userId) }
            val items = async { userService.getProducts(userId) }
            println("Got ${items.await()} for ${user.await()}")
            user.await() to items.await()
        }.await()
        val end = Instant.now().toEpochMilli()
        println("Done in ${end - start} millis")
        result
    }

    suspend fun getUsersAndItemsInBulk(userIds: List<String>): List<Pair<User, List<Product>>> = coroutineScope {
        val start = Instant.now().toEpochMilli()

        val deferred = userIds.map {
            async {
                val user = async { userService.getUser(it) }
                val proucts = async { userService.getProducts(it) }
                user to proucts
            }
        }
        val result = deferred.map {
            val pair = it.await()
            pair.first.await()to pair.second.await()
        }
        val end = Instant.now().toEpochMilli()
        result.forEach {
            println("Got ${it.second} for ${it.first}")
        }
        println("Done in ${end - start} millis")
        result
    }
}