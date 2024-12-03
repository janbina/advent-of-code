import kotlin.system.measureTimeMillis

fun main() {
    val expectedResults = mapOf(
        1 to (3246517 to 29379307),
        2 to (359 to 418),
        3 to (174960292 to 56275602),
    )

    println("Running tests")

    val time = measureTimeMillis {
        for (day in 1..3) {
            val result = solveDay(day, 2024)
            val expected = expectedResults[day]

            check(expected == result) {
                "Failed for day $day: Expected $expected but got $result"
            }
        }
    }

    println("All tests passed in $time ms.")
}