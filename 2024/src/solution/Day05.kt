package solution

import utils.split
import java.io.BufferedReader

class Day05(
    input: BufferedReader,
) : Day<Int, Int> {

    private val splits = input.readLines().split { it.isBlank() }
    private val rules = splits[0].toSet()
    private val updates = splits[1].map { line ->
        line.split(",").map { it.toInt() }
    }

    override fun part1(): Int {
        return updates.sumOf { update ->
            if (update.isInOrder(rules)) {
                update[update.size / 2]
            } else 0
        }
    }

    override fun part2(): Int {
        return updates.sumOf { update ->
            if (!update.isInOrder(rules)) {
                update.fixOrder(rules)[update.size / 2]
            } else 0
        }

    }

    private fun List<Int>.firstError(rules: Set<String>): Pair<Int, Int>? {
        for (i in 1..lastIndex) {
            for (j in 0..<i) {
                if ("${get(i)}|${get(j)}" in rules) {
                    return j to i
                }
            }
        }
        return null
    }

    private fun List<Int>.isInOrder(rules: Set<String>): Boolean {
        return firstError(rules) == null
    }

    private fun List<Int>.fixOrder(rules: Set<String>): List<Int> {
        val current = this.toMutableList()
        while (true) {
            val (j, i) = current.firstError(rules) ?: break
            current.add(j, current.removeAt(i))
        }
        return current.toList()
    }
}
