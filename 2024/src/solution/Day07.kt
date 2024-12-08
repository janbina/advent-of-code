package solution

import java.io.BufferedReader
import kotlin.math.absoluteValue
import kotlin.math.log10
import kotlin.math.pow

class Day07(
    input: BufferedReader,
) : Day<Long, Long> {

    private data class Eq(val test: Long, val nums: List<Long>)

    private val eqs = input.readLines().map {
        val a = it.substringBefore(':').toLong()
        val b = it.substringAfter(": ").split(' ').map { it.toLong() }
        Eq(a, b)
    }.asSequence()

    private fun multiplier(num: Long): Long {
        if (num == 0L) return 10
        return (10.0.pow(log10(num.absoluteValue.toDouble()).toInt() + 1)).toLong()
    }

    private fun Eq.canBeTrue(acc: Long = nums[0], i: Int = 1, canConcat: Boolean = false): Boolean {
        if (i > nums.lastIndex) return acc == test
        if (acc > test) return false
        return canBeTrue(acc + nums[i], i + 1, canConcat)
            || canBeTrue(acc * nums[i], i + 1, canConcat)
            || (canConcat && canBeTrue(acc * multiplier(nums[i]) + nums[i], i + 1, canConcat))
    }

    override fun part1(): Long {
        return eqs.filter { it.canBeTrue(canConcat = false) }.sumOf { it.test }
    }

    override fun part2(): Long {
        return eqs.filter { it.canBeTrue(canConcat = true) }.sumOf { it.test }
    }
}
