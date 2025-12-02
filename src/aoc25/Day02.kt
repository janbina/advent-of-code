package aoc25

import Day
import utils.digitCount
import java.io.BufferedReader

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
        return ranges.sumOf { range ->
            range.sumOf { num ->
                if (num.toString().isInvalid(numOfChunks = 2)) num else 0
            }
        }
    }

    override fun part2(): Long {
        fun String.isInvalid(): Boolean {
            return (2..length).any { numOfChunks ->
                isInvalid(numOfChunks)
            }
        }

        return ranges.sumOf { range ->
            range.sumOf { num ->
                if (num.toString().isInvalid()) num else 0
            }
        }
    }

    private fun String.isInvalid(numOfChunks: Int): Boolean {
        if (length % numOfChunks != 0) return false

        val digitsPerChunk = length / numOfChunks

        for (i in 0..<digitsPerChunk) {
            for (testChunk in 1..<numOfChunks) {
                if (get(i) != get(i + testChunk * digitsPerChunk)) return false
            }
        }

        return true
    }
}
