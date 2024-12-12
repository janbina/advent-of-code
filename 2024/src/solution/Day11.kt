package solution

import utils.*
import java.io.BufferedReader
import java.math.BigInteger
import java.util.*
import kotlin.collections.HashMap

class Day11(
    input: BufferedReader,
) : Day<Long, Long> {

    private val stones = input.readLines().first().split(" ").map { it.toLong() }

    override fun part1(): Long {
        return run(stones, 25)
    }

    override fun part2(): Long {
        return run(stones, 75)
    }

    private fun run(stones: List<Long>, blinks: Int): Long {
        var m1 = HashMap(stones.groupingBy { it }.eachCount().mapValues { it.value.toLong() })
        var m2 = hashMapOf<Long, Long>()
        fun add(s: Long, num: Long) {
            m2[s] = (m2[s] ?: 0) + num
        }
        repeat(blinks) {
            m2.clear()
            for ((stone, num) in m1) {
                val (s1, s2) = stone.blink()
                add(s1, num)
                s2?.let { add(s2, num) }
            }
            m1 = m2.also { m2 = m1 }
        }
        return m1.values.sum()
    }

    private fun Long.blink(): Pair<Long, Long?> {
        if (this == 0L) return 1L to null
        val dc = digitCount()
        if (dc % 2 == 0) {
            val halfPower = Math.pow(10.0, (dc / 2).toDouble()).toInt()
            return this / halfPower to this % halfPower
        }
        return this * 2024 to null
    }
}
