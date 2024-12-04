package solution

import utils.Move
import utils.Point2D
import java.io.BufferedReader

class Day04(
    input: BufferedReader,
) : Day<Int, Int> {

    private val grid = input.readLines()

    private fun List<String>.indices(): Sequence<Point2D> = sequence {
        for (y in indices) {
            for (x in get(y).indices) {
                yield(Point2D(x, y))
            }
        }
    }

    private fun getChar(p: Point2D) = grid.getOrNull(p.y)?.getOrNull(p.x) ?: '-'

    private fun getString(start: Point2D, direction: Move, length: Int): String {
        return (0..(length - 2)).runningFold(initial = start) { p, _ -> p.applyMove(direction) }
            .map { getChar(it) }.joinToString(separator = "")
    }

    override fun part1(): Int {
        return grid.indices().sumOf { p ->
            Move.all().count { move ->
                getString(p, move, 4) == "XMAS"
            }
        }
    }

    override fun part2(): Int {
        return grid.indices().count { p ->
            val a = getString(p.applyMove(Move.upLeft), Move.downRight, 3)
            val b = getString(p.applyMove(Move.downLeft), Move.upRight, 3)
            (a == "MAS" || a == "SAM") && (b == "MAS" || b == "SAM")
        }
    }
}
