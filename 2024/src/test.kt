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
        9 to (6435922584968 to 6469636832766),
        10 to (550 to 1255),
        11 to (197357L to 234568186890978),
        12 to (1370100 to 818286),
    )

    println("Running tests")

    val time = measureTimeMillis {
        for (day in 1..12) {
            val result = solveDay(day, 2024)
            val expected = expectedResults[day]

            check(expected == result) {
                "Failed for day $day: Expected $expected but got $result"
            }
        }
    }

    println("All tests passed in $time ms.")
}
