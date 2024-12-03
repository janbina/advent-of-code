package solution

import java.io.BufferedReader
import java.util.*

class Day02(
    input: BufferedReader,
) : Day<Int, Int> {

    private val reports = input.lineSequence().map { line ->
        line.split(" ").map { it.toInt() }
    }.toList()

    override fun part1(): Int {
        return reports.count { it.isSafe() }
    }

    override fun part2(): Int {
        return reports.count { it.isSafe(skipsAllowed = 1) }
    }

    private fun List<Int>.isSafe(
        safeDiffs: List<IntRange> = listOf(1..3, -3..-1),
        skipsAllowed: Int = 0,
    ): Boolean {
        val stack = Stack<State>()
        stack.addAll(
            safeDiffs.map { diff ->
                State(aI = 0, bI = 1, diff = diff, skips = 0)
            }
        )

        while (stack.isNotEmpty()) {
            val state = stack.pop()
            if (state.aI == lastIndex) return true
            if (state.bI > lastIndex) continue

            val diff = this[state.aI] - this[state.bI]
            if (diff in state.diff) {
                stack.push(
                    State(
                        aI = state.bI,
                        bI = state.bI + 1,
                        diff = state.diff,
                        skips = state.skips,
                    )
                )
            } else if (state.skips < skipsAllowed) {
                // special case when skipping the last item
                if (state.bI == lastIndex) return true

                // skipping a, special case when a is the first item
                if (state.aI == 0) {
                    stack.push(state.copy(aI = state.bI, bI = state.bI + 1, skips = state.skips + 1))
                } else {
                    stack.push(state.copy(aI = state.aI - 1, skips = state.skips + 1))
                }

                // skipping b
                stack.push(state.copy(bI = state.bI + 1, skips = state.skips + 1))
            }
        }

        return false
    }

    data class State(
        val aI: Int,
        val bI: Int,
        val diff: IntRange,
        val skips: Int,
    )
}
