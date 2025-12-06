package aoc25

import Day
import utils.Point2D
import utils.get
import utils.getOrNull
import utils.indices
import utils.product
import utils.set
import java.io.BufferedReader
import java.lang.Exception

class Day06(
    input: BufferedReader,
) : Day<Long, Long> {

    private val lines = input.readLines()

    override fun part1(): Long {
        val columns = lines.map { line ->
            line.split(Regex("\\s+")).dropLastWhile { it.isEmpty() }
        }

        var sum = 0L
        for (i in columns[0].indices) {
            val op = columns.last()[i]
            var num = if (op == "+") 0L else 1L
            for (j in 0..<columns.lastIndex) {
                if (op == "+") {
                    num += columns[j][i].toLong()
                } else {
                    num *= columns[j][i].toLong()
                }
            }
            sum += num
        }
        return sum
    }

    private fun parseColumn(index: Int): Pair<Long, Char?>? {
        val op = lines.last().getOrNull(index).takeIf { it == '+' || it == '*' }

        val num = buildString(lines.size - 1) {
            for (li in 0..<lines.lastIndex) {
                lines[li].getOrNull(index)?.let { append(it) }
            }
        }.trim().takeIf { it.isNotBlank() }?.toLong()

        return if (num != null) num to op else null
    }

    override fun part2(): Long {
        var sum = 0L
        var i = 0
        val maxI = lines.maxOf { it.lastIndex }
        while (i <= maxI) {
            var op: Char? = null
            val nums = mutableListOf<Long>()
            while (true) {
                val res = parseColumn(i++) ?: break
                require(op == null || res.second == null) { "Can't have two op characters" }
                op = op ?: res.second
                nums += res.first
            }
            sum += when (op) {
                '+' -> nums.sum()
                '*' -> nums.product()
                else -> error("Invalid op: $op")
            }
        }
        return sum
    }
}
