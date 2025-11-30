package utils

import kotlin.math.absoluteValue

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
}
