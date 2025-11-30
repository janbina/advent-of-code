package aoc24

import Day
import java.io.BufferedReader

class Day03(
    input: BufferedReader,
) : Day<Int, Int> {

    private val regex = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""")
    private val matches = input.lineSequence().map { regex.findAll(it) }.flatten().toList()

    override fun part1(): Int {
        return matches.sumOf {
            if (it.groupValues[0].startsWith("mul")) {
                it.groupValues[1].toInt() * it.groupValues[2].toInt()
            } else 0
        }
    }

    override fun part2(): Int {
        var canAdd = true
        var total = 0
        for (match in matches) {
            when (match.groupValues[0]) {
                "do()" -> canAdd = true
                "don't()" -> canAdd = false
                else -> if (canAdd) total += match.groupValues[1].toInt() * match.groupValues[2].toInt()
            }
        }
        return total
    }
}
