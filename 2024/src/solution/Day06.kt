package solution

import utils.Move
import utils.Point2D
import utils.getOrNull
import utils.indices
import java.io.BufferedReader

class Day06(
    input: BufferedReader,
) : Day<Int, Int> {

    private val map = input.readLines().map { it.toList() }

    private val guardDirs = mapOf(
        '>' to Move.right,
        '<' to Move.left,
        '^' to Move.up,
        'v' to Move.down,
    )
    private val guardStart = map.indices().first { map.getOrNull(it) in guardDirs.keys }
    private val guardStartDir = guardDirs[map.getOrNull(guardStart)] ?: error("invalid input")

    private val visitedp1 = mutableSetOf<Point2D>()

    override fun part1(): Int {
        var pos = guardStart
        var dir = guardStartDir

        while (true) {
            val c = map.getOrNull(pos) ?: break
            if (c == '#') {
                pos = pos.applyMove(dir.inverted())
                dir = dir.turnRight()
            } else {
                visitedp1 += pos
                pos = pos.applyMove(dir)
            }
        }

        return visitedp1.size
    }

    override fun part2(): Int {
        if (visitedp1.isEmpty()) part1()

        return visitedp1.count { findCycle(obstruction = it) }
    }

    // turns out this is much more efficient than creating and maintaining a hashset
    // for every iteration... who would've guessed?
    private val visited = Array(4) { Array(map.size) { BooleanArray(map[0].size) } }
    private val Move.id: Int
        get() = when (this) {
            Move.up -> 0
            Move.down -> 1
            Move.left -> 2
            Move.right -> 3
            else -> error("")
        }

    private fun isVisited(p: Point2D, m: Move): Boolean {
        return visited[m.id][p.y][p.x]
    }

    private fun setVisited(p: Point2D, m: Move) {
        visited[m.id][p.y][p.x] = true
    }

    private fun findCycle(
        obstruction: Point2D? = null,
    ): Boolean {
        visited.forEach { it.forEach { it.fill(false) } }

        var pos = guardStart
        var dir = guardStartDir

        while (true) {
            val c = map.getOrNull(pos) ?: return false // exited map
            if (isVisited(pos, dir)) return true // cycle found
            if (pos == obstruction || c == '#') {
                pos = pos.applyMove(dir.inverted())
                dir = dir.turnRight()
            } else {
                setVisited(pos, dir)
                pos = pos.applyMove(dir)
            }
        }
    }
}
