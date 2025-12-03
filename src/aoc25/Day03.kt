package aoc25

import Day
import java.io.BufferedReader

class Day03(
    input: BufferedReader,
) : Day<Long, Long> {

    private val ratings = input.readLines()

    override fun part1(): Long {
        return ratings.sumOf { it.maxJoltage(2) }
    }

    override fun part2(): Long {
        return ratings.sumOf { it.maxJoltage(12) }
    }

    private fun String.maxJoltage(batteriesNum: Int): Long {
        if (length <= batteriesNum) return toLong()
        var res = ""
        var lastUsed = -1

        while (res.length < batteriesNum) {
            val max = maxIndex(
                from = lastUsed + 1,
                to = lastIndex - batteriesNum + 1 + res.length,
            )
            res += get(max)
            lastUsed = max
        }


        return res.toLong()
    }

    private fun String.maxIndex(from: Int, to: Int): Int {
        var max = from
        for (i in from + 1..to) {
            if (get(i) > get(max)) {
                max = i
            }
        }
        return max
    }
}
