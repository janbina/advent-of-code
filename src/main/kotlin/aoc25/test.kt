package aoc25

import solveDay
import kotlin.system.measureTimeMillis

fun main() {
    val expectedResults = mapOf(
        1 to (1097L to 7101L),
        2 to (28846518423L to 31578210022L),
        3 to (17155L to 169685670469164L),
        4 to (1474L to 8910L),
        5 to (635L to 369761800782619L),
        6 to (4583860641327L to 11602774058280L),
        7 to (1667L to 62943905501815L),
        8 to (42840L to 170629052L),
        9 to (4748826374L to 1554370486L),
        10 to (375L to 15377L),
        11 to (708L to 545394698933400L),
        12 to (463L to 0L),
    )

    println("Running tests")

    val time = measureTimeMillis {
        for (day in 1..expectedResults.size) {
            val result = solveDay(day = day, year = 2025, print = false)
            val expected = expectedResults[day]

            check(expected == result) {
                "Failed for day $day: Expected $expected but got $result"
            }
        }
    }

    println("All tests passed in $time ms.")
}
