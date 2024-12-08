package solution

import utils.*
import java.io.BufferedReader

class Day08(
    input: BufferedReader,
) : Day<Any, Int> {

    private val lines = input.readLines().map { it.toList() }
    private val antennas: Map<Char, List<Point2D>> = lines.indices()
        .filter { lines[it] != '.' }
        .groupBy { lines[it] }
    private fun Point2D.inMap(): Boolean = x in lines[0].indices && y in lines.indices

    override fun part1(): Any {
        val locs = mutableSetOf<Point2D>()

        for ((_, ant) in antennas) {
            for ((a, b) in ant.uniquePairs()) {
                (a * 2 - b).takeIf { it.inMap() }?.let { locs += it }
                (b * 2 - a).takeIf { it.inMap() }?.let { locs += it }
            }
        }

        return locs.size
    }

    override fun part2(): Int {
        val locs = mutableSetOf<Point2D>()

        for ((_, ant) in antennas) {
            for ((a, b) in ant.uniquePairs()) {
                val diff = a - b
                generateSequence(a) { it + diff }
                    .takeWhile { it.inMap() }
                    .forEach { locs += it }

                generateSequence(a) { it - diff }
                    .takeWhile { it.inMap() }
                    .forEach { locs += it }
            }
        }

        return locs.size
    }
}
