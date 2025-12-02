package aoc25

import Day
import java.io.BufferedReader
import kotlin.math.abs

class Day02(
    input: BufferedReader,
) : Day<Long, Long> {

    private val ranges = input.readLines().first()
        .split(",")
        .map { range ->
            val (from, to) = range.split("-").map { it.toLong() }
            from..to
        }

    override fun part1(): Long {
        fun Long.isInvalid(): Boolean {
            val s = this.toString()
            if (s.length % 2 != 0) return false
            for (i in 0 until s.length / 2) {
                if (s[i] != s[s.length / 2 + i]) {
                    return false
                }
            }
            return true
        }

        return ranges.sumOf { range -> range.sumOf { num -> if (num.isInvalid()) num else 0} }
    }

    override fun part2(): Long {
        fun Long.isInvalid(): Boolean {
            val s = this.toString()
            return (2..s.length).any { numOfChunks ->
                s.length % numOfChunks == 0
                        && s.chunked(s.length / numOfChunks).toSet().size == 1
            }
        }

        return ranges.sumOf { range -> range.sumOf { num -> if (num.isInvalid()) num else 0} }
    }
}
