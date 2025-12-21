import java.io.BufferedReader

fun solveDay(day: Int, year: Int, print: Boolean = true): Pair<Any, Any>? {
    if (print) {
        println("solving day $day")
    }

    val inputReader = getDayInputFile(day, year)
        .onFailure { println("Cannot get input for day $day, year $year: $it") }
        .getOrNull()?.bufferedReader() ?: return null

    val start = System.currentTimeMillis()

    val solver = getSolver(day, year, inputReader)

    val p1Start = System.currentTimeMillis()
    val p1 = solver.part1()
    val p1Time = System.currentTimeMillis() - p1Start

    if (print) {
        println("\tpart 1 = $p1 in $p1Time ms")
    }

    val p2Start = System.currentTimeMillis()
    val p2 = solver.part2()
    val p2Time = System.currentTimeMillis() - p2Start

    val time = System.currentTimeMillis() - start

    if (print) {
        println("\tpart 2 = $p2 in $p2Time ms")
        println("total time: $time ms")
    }

    return p1 to p2
}

private fun getSolver(day: Int, year: Int, input: BufferedReader): Day<out Any, out Any> {
    val y = (year % 100).toString().padStart(2, '0')
    val d = day.toString().padStart(2, '0')
    val className = "aoc$y.Day$d"
    return try {
        Class.forName(className)
            .getConstructor(BufferedReader::class.java)
            .newInstance(input) as Day<out Any, out Any>
    } catch (_: ClassNotFoundException) {
        error("Day $day not yet implemented")
    }
}
