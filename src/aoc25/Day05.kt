package aoc25

import Day
import utils.Point2D
import utils.get
import utils.getOrNull
import utils.indices
import utils.set
import java.io.BufferedReader

class Day05(
    input: BufferedReader,
) : Day<Int, Long> {

    private val lines = input.readLines()
    private val ranges = lines
        .takeWhile { it.isNotBlank() }
        .map { line ->
            val (a, b) = line.split("-").map { it.toLong() }
            a..b
        }
    private val ingredients = lines
        .dropWhile { it.isNotBlank() }
        .drop(1)
        .map { it.toLong() }

    override fun part1(): Int {
        return ingredients.count { ing -> ranges.any { range -> ing in range } }
    }

    override fun part2(): Long {
        val mut = ranges.toMutableList()

        operator fun LongRange.contains(other: LongRange): Boolean {
            return other.first >= first && other.last <= last
        }

        for (i in mut.indices) {
            for (j in  (i + 1)..mut.lastIndex) {
                val r1 = mut[i]
                val r2 = mut[j]
                when {
                    r1 in r2 -> mut[i] = LongRange.EMPTY
                    r2 in r1 -> mut[j] = LongRange.EMPTY
                    r1.last in r2 -> mut[i] = r1.first..<r2.first
                    r1.first in r2 -> mut[i] = (r2.last + 1)..r1.last
                }
                if (mut[i].isEmpty()) break
            }
        }

        return mut.sumOf { it.last - it.first + 1 }
    }
}
