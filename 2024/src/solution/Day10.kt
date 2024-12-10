package solution

import utils.*
import java.io.BufferedReader
import java.util.*

class Day10(
    input: BufferedReader,
) : Day<Int, Int> {

    private val map = input.readLines().map { line -> line.map { c -> c.digitToInt() } }

    private fun Point2D.isStart() = map.getOrNull(this) == 0
    private fun Point2D.isEnd() = map.getOrNull(this) == 9
    private fun Point2D.next() = neighbors().filter { neighbor ->
        map[this] + 1 == map.getOrNull(neighbor)
    }

    override fun part1(): Int {
        val visited = mutableSetOf<Point2D>()
        val q = LinkedList<Point2D>()
        return map.indices().filter { it.isStart() }.sumOf { start ->
            visited.clear()
            q += start

            while (true) {
                val c = q.pollFirst() ?: break
                visited += c
                q += c.next().filter { it !in visited }
            }

            visited.count { it.isEnd() }
        }
    }

    override fun part2(): Int {
        val q = LinkedList<Point2D>()
        val cnt = mutableMapOf<Point2D, Int>()
        return map.indices().filter { it.isStart() }.sumOf { start ->
            cnt.clear()
            q += start
            cnt += start to 1

            while (true) {
                val c = q.pollFirst() ?: break
                c.next().forEach {
                    if (it !in cnt) q += it
                    cnt[it] = (cnt[it] ?: 0) + cnt.getOrErr(c)
                }
            }

            cnt.sumOf { if (it.key.isEnd()) it.value else 0 }
        }
    }
}
