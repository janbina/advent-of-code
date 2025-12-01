package aoc25

import solveDay
import kotlin.system.measureTimeMillis

fun main() {
    val expectedResults = mapOf(
        1 to (1097 to 7101),
    )

    println("Running tests")

    val time = measureTimeMillis {
        for (day in 1..1) {
            val result = solveDay(day, 2025)
            val expected = expectedResults[day]

            check(expected == result) {
                "Failed for day $day: Expected $expected but got $result"
            }
        }
    }

    println("All tests passed in $time ms.")
}
