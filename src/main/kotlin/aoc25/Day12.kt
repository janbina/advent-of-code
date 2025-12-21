package aoc25

import Day
import solveDay
import utils.Point2D
import utils.split
import java.io.BufferedReader

fun main() { solveDay(12, 2025) }

class Day12(
    input: BufferedReader,
) : Day<Long, Long> {

    private val sections = input.readLines().split { it.isBlank() }

    private val presents = sections.dropLast(1).map { lines ->
        val w = lines[1].length
        val h = lines.size - 1
        require(w == h)
        Present(
            size = w,
            points = buildSet {
                for (x in 0..<w) {
                    for (y in 0..<h) {
                        if (lines[y + 1][x] == '#') {
                            add(Point2D(x, y))
                        }
                    }
                }
            }
        )
    }

    private val regions = sections.last().map { line ->
        val s = line.split("x", " ")
        Region(
            w = s[0].toInt(),
            h = s[1].dropLast(1).toInt(),
            presentNums = s.drop(2).map { it.toInt() },
        )
    }

    private data class Present(
        val size: Int, // we only allow square presents
        val points: Set<Point2D>,
    ) {
        val allRotations by lazy {
            buildSet {
                add(points)
                add(points.rotate90(size))
                add(points.rotate90(size).rotate90(size))
                add(points.rotate90(size).rotate90(size).rotate90(size))
                add(points.flip(size))
                add(points.flip(size).rotate90(size))
                add(points.flip(size).rotate90(size).rotate90(size))
                add(points.flip(size).rotate90(size).rotate90(size).rotate90(size))
            }
        }
    }

    private data class Region(
        val w: Int,
        val h: Int,
        val presentNums: List<Int>,
    )

    private fun canPlace(
        occupied: Set<Point2D>,
        points: Set<Point2D>,
        topLeft: Point2D,
    ): Set<Point2D>? {
        for (p in points) {
            if ((p + topLeft) in occupied) return null
        }
        return occupied + points.map { it + topLeft }
    }

    // this works by trying to put every rotation of every gift to every possible location
    // until it eventually fits them all or finds out it's not possible
    // it would be extremely slow, but should work
    // in reality, it's only needed for the example input, real input is nice
    private fun fitting(
        rw: Int, rh: Int,
        occupied: Set<Point2D>,
        remainingPresents: List<Int>,
    ): Boolean {
        if (remainingPresents.sum() == 0) return true
        val presentIndex = remainingPresents.indexOfFirst { it > 0 }
        val present = presents[presentIndex]
        for (x in 0..rw - present.size) {
            for (y in 0..rh - present.size) {
                for (rotation in present.allRotations) {
                    val newOccupied = canPlace(occupied, rotation, Point2D(x, y))
                    if (newOccupied != null) {
                        val fitting = fitting(
                            rw, rh,
                            newOccupied,
                            remainingPresents.toMutableList().apply {
                                set(presentIndex, get(presentIndex) - 1)
                            },
                        )
                        if (fitting) return true
                    }
                }
            }
        }
        return false
    }

    private fun Region.fitsAll(): Boolean {
        val regionArea = w * h
        val presentsArea = presentNums.mapIndexed { i, num ->
            presents[i].points.size * num
        }.sum()
        if (presentsArea > regionArea) return false
        val presentSlots = (w / 3) * (h / 3)
        if (presentSlots >= presentNums.sum()) return true
        return fitting(w, h, emptySet(), presentNums)
    }

    override fun part1(): Long {
        return regions.count { it.fitsAll() }.toLong()
    }

    override fun part2(): Long {
        return 0
    }
}

private fun Set<Point2D>.rotate90(squareSize: Int): Set<Point2D> {
    return HashSet<Point2D>(size).apply {
        for (p in this@rotate90) {
            add(Point2D(x = squareSize - 1 - p.y, y = p.x))
        }
    }
}

private fun Set<Point2D>.flip(squareSize: Int): Set<Point2D> {
    return HashSet<Point2D>(size).apply {
        for (p in this@flip) {
            add(Point2D(x = squareSize - 1 - p.x, y = p.y))
        }
    }
}
