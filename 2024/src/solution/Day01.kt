package solution

import java.io.BufferedReader
import kotlin.math.abs

class Day01(
    input: BufferedReader,
) : Day<Int, Int> {

    private val lists = input.lineSequence()
        .map { it.split("   ") }
        .map { it[0].toInt() to it[1].toInt() }
        .unzip()
    private val l = lists.first.sorted()
    private val r = lists.second.sorted()

    override fun part1(): Int {
        return l.indices.sumOf { abs(l[it] - r[it]) }
    }

    override fun part2(): Int {
        val rCounts = r.groupingBy { it }.eachCount()
        return l.sumOf { it * (rCounts.getOrDefault(it, 0)) }
    }
}
