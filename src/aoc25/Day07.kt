package aoc25

import Day
import utils.Point2D
import utils.getOrNull
import java.io.BufferedReader

class Day07(
    input: BufferedReader,
) : Day<Long, Long> {

    private val map = input.readLines().map { it.toList() }
    private val start = map.withIndex().firstNotNullOf { (y, line) ->
        line.indexOf('S').takeIf { it >= 0 }?.let { Point2D(it, y) }
    }

    override fun part1(): Long {
        var totalSplits = 0L
        var points = mutableSetOf(start)

        while (points.isNotEmpty()) {
            val newPoints = mutableSetOf<Point2D>()
            for (point in points) {
                val c = map.getOrNull(point)
                when (c) {
                    'S', '.' -> newPoints += point.down()
                    '^' -> {
                        newPoints += point.downLeft()
                        newPoints += point.downRight()
                        totalSplits++
                    }
                }
            }
            points = newPoints
        }

        return totalSplits
    }

    override fun part2(): Long {
        val cache = mutableMapOf<Point2D, Long>()
        fun countSplits(start: Point2D): Long = cache.getOrPut(start) {
            val c = map.getOrNull(start)
            when (c) {
                null -> 1L
                '^' -> countSplits(start.downLeft()) + countSplits(start.downRight())
                else -> countSplits(start.down())
            }
        }
        return countSplits(start)
    }
}
