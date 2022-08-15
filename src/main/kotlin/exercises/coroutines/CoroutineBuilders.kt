package exercises.coroutines

import kotlinx.coroutines.*
import service.UserService
import java.time.Instant

val userService = UserService()

fun main() {
    getUserAndItems("101")

    getUserAndItemsCoroutine("102")

    getUserAndItemsConcurrent("103")
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

fun getUserAndItems(userId: String) {
    val start = Instant.now().toEpochMilli()
    val user = userService.getUser(userId)
    val items = userService.getProducts(userId)
    println("Got $items for $user")
    val end = Instant.now().toEpochMilli()
    println("Done in ${end-start} millis")
}

fun getUserAndItemsCoroutine(userId: String) = runBlocking {
    val start = Instant.now().toEpochMilli()
    launch {
        val user = userService.getUser(userId)
        val items = userService.getProducts(userId)
        println("Got $items for $user")
    }.join()
    val end = Instant.now().toEpochMilli()
    println("Done in ${end-start} millis")
}

fun getUserAndItemsConcurrent(userId: String) = runBlocking {
    val start = Instant.now().toEpochMilli()
    launch() {
        val user = async { userService.getUser(userId) }
        val items = async { userService.getProducts(userId) }
        println("Got ${items.await()} for ${user.await()}")
    }.join()
    val end = Instant.now().toEpochMilli()
    println("Done in ${end - start} millis")
}