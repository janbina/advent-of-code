import solution.*
import java.io.BufferedReader
import kotlin.system.measureTimeMillis

fun main() {
    solveDay(day = 1, year = 2024)
}

fun solveDay(day: Int, year: Int) {
    val inputReader = getDayInputFile(day, 2023)
        .onFailure { println("Cannot get input for day $day, year $year: $it") }
        .getOrNull()?.bufferedReader() ?: return
    val solver = getSolver(day, inputReader)

    measureTimeMillis {
        println("solving day $day")
        println("\tpart 1 = ${solver.solvePart1()}")
        println("\tpart 2 = ${solver.solvePart2()}")
    }.also {
        println("time taken: $it ms")
    }
}

fun getSolver(day: Int, input: BufferedReader): Day<*, *> {
    return when (day) {
        1 -> Day01(input)
        else -> error("Day $day not yet implemented")
    }
}
