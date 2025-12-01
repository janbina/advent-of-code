package aoc25

import Day
import java.io.BufferedReader
import kotlin.math.abs

class Day01(
    input: BufferedReader,
) : Day<Int, Int> {

    private val add = input.readLines().map {
        val num = it.drop(1).toInt()
        when (it.first()) {
            'R' -> num
            'L' -> -num
            else -> error("invalid input")
        }
    }

    override fun part1(): Int {
        var num = 50
        var sum = 0

        for (x in add) {
            num = (num + x) % 100
            if (num == 0) sum++
        }

        return sum
    }

    override fun part2(): Int {
        var num = 50
        var sum = 0

        for (x in add) {
            val prev = num
            num = (num + x) % 100
            if (num < 0) num += 100
            sum += abs(x) / 100
            if (
                num == 0
                || (prev > num && x > 0)
                || (prev < num && x < 0 && prev != 0)
            ) {
                sum++
            }
        }

        return sum
    }
}
