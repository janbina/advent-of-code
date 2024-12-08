import solution.*
import java.io.BufferedReader
import kotlin.system.measureTimeMillis

fun main() {
    val day = 8
    println("solving day $day")
    measureTimeMillis {
        val res = solveDay(day = day, year = 2024)
        println("\tpart 1 = ${res?.first}")
        println("\tpart 2 = ${res?.second}")
    }.also {
        println("time taken: $it ms")
    }
}

fun solveDay(day: Int, year: Int): Pair<Any, Any>? {
    val inputReader = getDayInputFile(day, year)
        .onFailure { println("Cannot get input for day $day, year $year: $it") }
        .getOrNull()?.bufferedReader() ?: return null
    val solver = getSolver(day, inputReader)

    return solver.part1() to solver.part2()
}

private fun getSolver(day: Int, input: BufferedReader): Day<out Any, out Any> {
    return when (day) {
        1 -> Day01(input)
        2 -> Day02(input)
        3 -> Day03(input)
        4 -> Day04(input)
        5 -> Day05(input)
        6 -> Day06(input)
        7 -> Day07(input)
        8 -> Day08(input)
        else -> error("Day $day not yet implemented")
    }
}
