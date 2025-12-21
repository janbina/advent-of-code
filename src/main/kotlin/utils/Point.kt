package utils

import java.lang.Math.toDegrees
import kotlin.math.abs
import kotlin.math.atan2

fun Point2D(x: Int, y: Int): Point2D {
    return Point2D(packInts(x, y))
}

@JvmInline
value class Point2D(
    val packedValue: Long,
) {

    val x: Int get() = unpackInt1(packedValue)
    val y: Int get() = unpackInt2(packedValue)

    fun up() = applyMove(Move.up)
    fun down() = applyMove(Move.down)
    fun left() = applyMove(Move.left)
    fun right() = applyMove(Move.right)

    fun upLeft() = applyMove(Move.upLeft)
    fun upRight() = applyMove(Move.upRight)
    fun downLeft() = applyMove(Move.downLeft)
    fun downRight() = applyMove(Move.downRight)

    fun distanceTo(other: Point2D): Int =
        abs(x - other.x) + abs(y - other.y)

    // Note: Tested only with x,y in screen mode (upper left)
    fun angleTo(other: Point2D): Double {
        val d = toDegrees(
            atan2(
                (other.y - y).toDouble(),
                (other.x - x).toDouble()
            )
        ) + 90
        return if (d < 0) d + 360 else d
    }

    fun neighbors(): List<Point2D> =
        listOf(up(), down(), left(), right())

    fun adjacent(): List<Point2D> =
        listOf(up(), down(), left(), right(), upLeft(), upRight(), downLeft(), downRight())

    fun applyMove(move: Move) = copy(x = x + move.dx, y = y + move.dy)

    fun keepMoving(move: Move) = sequence {
        var prev = this@Point2D
        while (true) {
            prev = prev.applyMove(move).also {
                yield(it)
            }
        }
    }

    operator fun plus(other: Point2D): Point2D {
        return Point2D(x = x + other.x, y = y + other.y)
    }

    operator fun minus(other: Point2D): Point2D {
        return Point2D(x = x - other.x, y = y - other.y)
    }

    operator fun times(k: Int): Point2D {
        return Point2D(x = k * x, y = k * y)
    }

    fun copy(
        x: Int = this.x,
        y: Int = this.y,
    ): Point2D {
        return Point2D(x = x, y = y)
    }

    override fun toString(): String {
        return "Point2D(x=$x, y=$y)"
    }

    companion object {
        val ORIGIN = Point2D(0, 0)
        val readerOrder: Comparator<Point2D> = Comparator { o1, o2 ->
            when {
                o1.y != o2.y -> o1.y - o2.y
                else -> o1.x - o2.x
            }
        }
    }
}

fun Move(dx: Int, dy: Int): Move {
    return Move(packInts(dx, dy))
}

@JvmInline
value class Move(
    val packedValue: Long,
) {
    val dx: Int get() = unpackInt1(packedValue)
    val dy: Int get() = unpackInt2(packedValue)

    fun inverted() = Move(dx * -1, dy * -1)

    fun turnRight() = Move(dy * -1, dx)

    operator fun times(multiplier: Int): Move {
        return Move(dx = dx * multiplier, dy = dy * multiplier)
    }

    override fun toString(): String {
        return "Move(dx=$dx, dy=$dy)"
    }

    companion object {
        val up = Move(0, -1)
        val down = Move(0, 1)
        val left = Move(-1, 0)
        val right = Move(1, 0)

        val upLeft = Move(-1, -1)
        val upRight = Move(1, -1)
        val downLeft = Move(-1, 1)
        val downRight = Move(1, 1)

        fun all() = listOf(
            up,
            down,
            left,
            right,
            upLeft,
            upRight,
            downLeft,
            downRight,
        )
    }
}

private inline fun packInts(val1: Int, val2: Int): Long {
    return (val1.toLong() shl 32) or (val2.toLong() and 0xFFFFFFFF)
}

private inline fun unpackInt1(value: Long): Int {
    return (value shr 32).toInt()
}

private inline fun unpackInt2(value: Long): Int {
    return (value and 0xFFFFFFFF).toInt()
}
