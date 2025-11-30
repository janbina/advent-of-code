import java.io.BufferedReader

fun solveDay(day: Int, year: Int): Pair<Any, Any>? {
    val inputReader = getDayInputFile(day, year)
        .onFailure { println("Cannot get input for day $day, year $year: $it") }
        .getOrNull()?.bufferedReader() ?: return null
    val solver = getSolver(day, year, inputReader)

    return solver.part1() to solver.part2()
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
