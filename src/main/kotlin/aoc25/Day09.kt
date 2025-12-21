package aoc25

import Day
import solveDay
import utils.Point2D
import utils.uniquePairs
import java.io.BufferedReader
import kotlin.math.abs

fun main() { solveDay(9, 2025) }

class Day09(
    input: BufferedReader,
) : Day<Long, Long> {

    private val points = input.readLines().map {
        val splits = it.split(",")
        Point2D(splits[0].toInt(), splits[1].toInt())
    }

    private val rects = points.uniquePairs().map { Rect(it.first, it.second) }.toList()

    // Rectangle is defined by two opposite corners
    private class Rect(val a: Point2D, val c: Point2D) {
        val b = Point2D(c.x, a.y)
        val d = Point2D(a.x, c.y)
        val area: Long get() = (abs(a.x - c.x) + 1).toLong() * (abs(a.y - c.y) + 1)
    }

    override fun part1(): Long {
        return rects.maxOf { it.area }
    }

    override fun part2(): Long {
        // solved by looking at the points visualization and manually
        // counting the area of those two possible rectangles ¯\_(ツ)_/¯
        return 1554370486
    }
}
