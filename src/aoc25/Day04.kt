package aoc25

import Day
import utils.Point2D
import utils.get
import utils.getOrNull
import utils.indices
import utils.set
import java.io.BufferedReader

class Day04(
    input: BufferedReader,
) : Day<Int, Int> {

    private val originalGrid = input.readLines().map { it.toList() }

    private fun Point2D.isAccessible(grid: List<List<Char>>): Boolean {
        if (grid[this] != '@') return false
        return adjacent().count { grid.getOrNull(it) == '@' } < 4
    }

    override fun part1(): Int {
        return originalGrid.indices().count { it.isAccessible(originalGrid) }
    }

    override fun part2(): Int {
        val grid = originalGrid.mapTo(ArrayList()) { it.toMutableList() }
        var sum = 0

        while (true) {
            val accessible = grid.indices().filter { it.isAccessible(grid) }
            var num = 0
            for (p in accessible) {
                grid[p] = '.'
                num++
            }
            if (num == 0) break
            sum += num
        }

        return sum
    }
}
