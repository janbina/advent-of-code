import kotlin.system.measureTimeMillis

fun main() {
    val expectedResults = mapOf(
        1 to (3246517 to 29379307),
        2 to (359 to 418),
        3 to (174960292 to 56275602),
        4 to (2336 to 1831),
        5 to (4814 to 5448),
        6 to (5531 to 2165),
        7 to (5702958180383 to 92612386119138),
        8 to (295 to 1034),
    )

    println("Running tests")

    val time = measureTimeMillis {
        for (day in 1..8) {
            val result = solveDay(day, 2024)
            val expected = expectedResults[day]

            check(expected == result) {
                "Failed for day $day: Expected $expected but got $result"
            }
        }
    }

    println("All tests passed in $time ms.")
}
