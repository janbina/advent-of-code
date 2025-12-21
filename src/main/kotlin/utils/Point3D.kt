package utils

import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int,
) {

    operator fun plus(other: Point3D): Point3D {
        return Point3D(x = x + other.x, y = y + other.y, z = z + other.z)
    }

    operator fun minus(other: Point3D): Point3D {
        return Point3D(x = x - other.x, y = y - other.y, z = z - other.z)
    }

    fun manhattanDistanceTo(other: Point3D): Int {
        return (x - other.x).absoluteValue +
                (y - other.y).absoluteValue +
                (z - other.z).absoluteValue
    }

    fun euclideanDistanceTo(other: Point3D): Double {
        val xp = (x - other.x).toDouble().pow(2)
        val yp = (y - other.y).toDouble().pow(2)
        val zp = (z - other.z).toDouble().pow(2)
        return sqrt(xp + yp + zp)
    }
}
