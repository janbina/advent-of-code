import kotlin.system.measureTimeMillis

fun main() {
    val year = 2025
    val day = 2
    println("solving day $day")
    measureTimeMillis {
        val res = solveDay(day = day, year = year)
        println("\tpart 1 = ${res?.first}")
        println("\tpart 2 = ${res?.second}")
    }.also {
        println("time taken: $it ms")
    }
}


