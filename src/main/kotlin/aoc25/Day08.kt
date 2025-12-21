package aoc25

import Day
import solveDay
import utils.Point3D
import utils.productOf
import utils.uniquePairs
import java.io.BufferedReader
import kotlin.collections.plusAssign

fun main() { solveDay(8, 2025) }

class Day08(
    input: BufferedReader,
) : Day<Long, Long> {

    private val points = input.readLines().map { line ->
        val (x, y, z) = line.split(",").map { it.toInt() }
        Point3D(x, y, z)
    }
    private val pointPairs = points.uniquePairs().toList().sortedBy { (a, b) -> a.euclideanDistanceTo(b) }

    override fun part1(): Long {
        val components = mutableListOf<MutableSet<Point3D>>()

        repeat(1000) { index ->
            val pair = pointPairs[index]
            components.addConnection(pair)
        }

        return components.sortedByDescending { it.size }.take(3).productOf { it.size.toLong() }
    }

    override fun part2(): Long {
        val components = points.mapTo(ArrayList()) { mutableSetOf(it) }

        for (pair in pointPairs) {
            components.addConnection(pair)
            if (components.size == 1) {
                return pair.first.x * pair.second.x.toLong()
            }
        }

        error("")
    }

    private fun MutableList<MutableSet<Point3D>>.addConnection(pair: Pair<Point3D, Point3D>) {
        val c1 = indexOfFirst { pair.first in it }
        val c2 = indexOfFirst { pair.second in it }
        when {
            c1 < 0 && c2 < 0 -> this += mutableSetOf(pair.first, pair.second)
            c1 < 0 && c2 >= 0 -> this[c2] += pair.first
            c1 >= 0 && c2 < 0 -> this[c1] += pair.second
            c1 != c2 -> {
                this[c1].addAll(this[c2])
                this.removeAt(c2)
            }
        }
    }
}
