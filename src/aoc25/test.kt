package aoc25

import solveDay
import kotlin.system.measureTimeMillis

fun main() {
    val expectedResults = mapOf(
        1 to (1097 to 7101),
        2 to (28846518423 to 31578210022),
        3 to (17155L to 169685670469164),
    )

    println("Running tests")

    val time = measureTimeMillis {
        for (day in 1..expectedResults.size) {
            val result = solveDay(day, 2025)
            val expected = expectedResults[day]

            check(expected == result) {
                "Failed for day $day: Expected $expected but got $result"
            }
        }
    }

    println("All tests passed in $time ms.")
}
