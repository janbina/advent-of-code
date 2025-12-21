package aoc24

import Day
import utils.*
import java.io.BufferedReader
import java.util.*

private typealias Region = Set<Point2D>

class Day12(
    input: BufferedReader,
) : Day<Int, Int> {

    private val map = input.readLines().map { it.toList() }
    private val regions: List<Region> by lazy {
        val assigned = mutableSetOf<Point2D>()
        map.indices().filter { it !in assigned }.map { start ->
            getRegion(start).also { assigned += it }
        }.toList()
    }

    override fun part1(): Int {
        return regions.sumOf { it.size * it.perimeter() }
    }

    override fun part2(): Int {
        return regions.sumOf { it.size * it.sides() }
    }

    private fun getRegion(start: Point2D): Set<Point2D> {
        val region = mutableSetOf(start)
        val q = LinkedList(listOf(start))
        while (true) {
            val x = q.pollFirst() ?: break
            val next = x.neighbors().filter { it !in region && map.getOrNull(it) == map[start] }
            region += next
            q += next
        }
        return region
    }

    private fun Region.perimeter(): Int {
        return sumOf { it.neighbors().count { n -> map.getOrNull(n) != map[this.first()] } }
    }

    private fun Region.sides(): Int {
        fun countSides(side: Move, groupBy: (Point2D) -> Int, sortBy: (Point2D) -> Int): Int {
            return filter { map.getOrNull(it.applyMove(side)) != map[it] }
                .groupBy(groupBy).values.sumOf { group ->
                    group.sortedBy(sortBy).windowed(2).count { sortBy(it[0]) + 1 != sortBy(it[1]) } + 1
                }
        }
        return countSides(Move.up, { it.y }, { it.x }) +
            countSides(Move.down, { it.y }, { it.x }) +
            countSides(Move.left, { it.x }, { it.y }) +
            countSides(Move.right, { it.x }, { it.y })
    }
}
