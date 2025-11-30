package aoc24

import Day
import java.io.BufferedReader

class Day09(
    input: BufferedReader,
) : Day<Long, Long> {

    private val blocks = input.readLines().first().map { it.digitToInt() }

    private fun List<Int>.fileIdBlockStream(): Sequence<Int> = sequence {
        var i = 0
        var j = lastIndex
        var blocksAssigned = 0

        while (i < j) {
            if (i % 2 == 0) {
                // file
                repeat(get(i)) { yield(i / 2) }
            } else {
                // empty space
                var blocksToAssign = get(i)
                while (blocksToAssign > 0 && i < j) {
                    if (j % 2 == 0) {
                        // j file
                        val assigningNow = (get(j) - blocksAssigned).coerceAtMost(blocksToAssign)
                        blocksToAssign -= assigningNow
                        repeat(assigningNow) { yield(j / 2) }
                        if (blocksToAssign > 0) {
                            j--
                            blocksAssigned = 0
                        } else {
                            blocksAssigned += assigningNow
                        }
                    } else {
                        // skip space
                        j--
                    }
                }
            }
            i++
        }

        if (blocksAssigned != 0) {
            repeat(get(j) - blocksAssigned) { yield(j / 2) }
        }
    }

    override fun part1(): Long {
        return blocks.fileIdBlockStream().withIndex().sumOf {
            it.index.toLong() * it.value
        }
    }

    private sealed interface DiskSpace {
        data class File(val id: Int, val size: Int) : DiskSpace
        data class Space(val size: Int) : DiskSpace
        data class Finalized(val id: Int, val size: Int) : DiskSpace
    }

    override fun part2(): Long {
        val files = mutableListOf<DiskSpace>()
        for (i in blocks.indices) {
            if (i % 2 == 0) {
                files += DiskSpace.File(i / 2, blocks[i])
            } else {
                files += DiskSpace.Space(blocks[i])
            }
        }
        var i = 0
        while (i < files.lastIndex) {
            val j = files.lastIndex - i
            val c = files[j] as? DiskSpace.File
            if (c != null) {
                for (si in 0 until j) {
                    val s = files[si]
                    if (s is DiskSpace.Space && s.size >= c.size) {
                        files.set(j, DiskSpace.Space(c.size))
                        files.removeAt(si)
                        if (s.size > c.size) {
                            files.add(si, DiskSpace.Space(s.size - c.size))
                        }
                        files.add(si, DiskSpace.Finalized(size = c.size, id = c.id))
                        break
                    }
                }
            }
            i++
        }

        i = 0
        var sum = 0L

        for (f in files) {
            when (f) {
                is DiskSpace.Space -> i += f.size
                is DiskSpace.File -> {
                    repeat(f.size) {
                        sum += i++ * f.id
                    }
                }

                is DiskSpace.Finalized -> {
                    repeat(f.size) {
                        sum += i++ * f.id
                    }
                }
            }
        }

        return sum
    }
}
