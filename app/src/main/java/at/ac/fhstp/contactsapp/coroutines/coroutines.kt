package at.ac.fhstp.contactsapp.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main () {
    // measureTimeMillis - measures how many milliseconds it took to run the program
    val duration = measureTimeMillis {
        // runBlocking - this function blocks the current thread until all the coroutines inside it complete.
        runBlocking {
            printWeatherReport()
        }
        }
    println("The program took $duration ms")
}

/*
 suspend fun - has the ability to pause and resume the main thread.
 It accounts for halting the main thread temporarily and proceeding it,
 when a function finished its operation.
 */
suspend fun printWeatherReport() {
    println("1: Thread: " +  Thread.currentThread().name)
    //coroutineScope - helps to group coroutines together, making sure that all child coroutines complete before continuing with the rest of the code.
    coroutineScope {
        println("2: Thread: " +  Thread.currentThread().name)

        /*
        In this case, "async" helps to run the weatherDeferred and temperatureDeferred concurrently.
        It's like creating sub-threads that goes in parallel.
         */
        //Dispatchers.IO - helps to switch to another thread.
        val weatherDeferred = async(Dispatchers.IO) {
            println("3: Thread: " + Thread.currentThread().name)
            getWeather()
        }
        val temperatureDeferred = async { getTemperature() }

        //Always use await after async.
        //Async - starts the thread. Await - waits for this thread until it reaches a certain line.
        val weather = weatherDeferred.await()
        val temperature = temperatureDeferred.await()

        /*
        launch - runs like side effect. It creates a new coroutine and simply runs it in the background
        without interrupting the main thread.
        It will run after "The weather is Sunny and the temperature is 12C".
         */
        launch {
            delay(400)
            println("Hiho")
        }
        println("The weather is $weather and the temperature is $temperature")
    }
}

suspend fun getWeather(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(2000)
    return  "12C"
}

