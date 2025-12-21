package aoc24

import Day
import utils.*
import java.io.BufferedReader

class Day13(
    input: BufferedReader,
) : Day<Long, Long> {

    private data class Machine(
        val aDiff: Point2D,
        val bDiff: Point2D,
        val prize: Point2D,
    )

    private val machines: List<Machine> by lazy {
        fun String.toPoint(): Point2D {
            return split(", ").map { it.filter { c -> c.isDigit() }.toInt() }
                .let { (x, y) -> Point2D(x, y) }
        }
        input.lineSequence().filter { it.isNotEmpty() }.chunked(3).map { (a, b, p) ->
            Machine(a.toPoint(), b.toPoint(), p.toPoint())
        }.toList()
    }

    override fun part1(): Long {
        return machines.sumOf { it.solve(0L) }
    }

    override fun part2(): Long {
        return machines.sumOf { it.solve(10000000000000) }
    }

    private fun Machine.solve(conversionError: Long): Long {
        val targetX = prize.x + conversionError
        val targetY = prize.y + conversionError

        val r = targetX * aDiff.y - targetY * aDiff.x
        val l = aDiff.y * bDiff.x - aDiff.x * bDiff.y
        if (r % l != 0L) return 0L
        val y = r / l
        val r2 = targetY - bDiff.y * y
        val l2 = aDiff.y
        if (r2 % l2 != 0L) return 0L
        val x = r2 / l2
        return 3 * x + y
    }
}
