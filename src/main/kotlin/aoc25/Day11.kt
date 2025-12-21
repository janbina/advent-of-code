package aoc25

import Day
import solveDay
import java.io.BufferedReader
import kotlin.collections.plusAssign

fun main() { solveDay(11, 2025) }

class Day11(
    input: BufferedReader,
) : Day<Long, Long> {

    private val reversedEdges: Map<String, Set<String>> = mutableMapOf<String, MutableSet<String>>().apply {
        input.lineSequence().forEach { line ->
            val (from, rest) = line.split(": ")
            rest.split(" ").forEach { to ->
                getOrPut(to) { mutableSetOf() } += from
            }
        }
    }

    private fun numberOfWays(
        from: String,
        to: String,
        via: Set<String> = emptySet(),
        cache: MutableMap<Pair<String, Set<String>>, Long> = mutableMapOf(),
    ): Long {
        if (to == from) return if (via.isEmpty()) 1 else 0
        return cache.getOrPut(to to via) {
            reversedEdges[to]?.sumOf {
                numberOfWays(
                    from = from,
                    to = it,
                    via = via - it,
                    cache = cache,
                )
            } ?: 0
        }
    }

    override fun part1(): Long {
        return numberOfWays(from = "you", to = "out")
    }

    override fun part2(): Long {
        // it would be easier to simply do svr->dac * dac->fft * fft->out plus the other way, but this is generic
        return numberOfWays(from = "svr", to = "out", via = setOf("dac", "fft"))
    }
}
