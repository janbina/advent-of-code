package utils

import kotlin.math.absoluteValue

fun <T> List<T>.uniquePairs(): Sequence<Pair<T, T>> = sequence {
    for (i in 0..lastIndex) {
        for (j in i + 1..lastIndex) {
            yield(get(i) to get(j))
        }
    }
}

fun <T> List<T>.pairs(): Sequence<Pair<T, T>> = sequence {
    for (i in indices) {
        for (j in indices) {
            if (i != j) {
                yield(get(i) to get(j))
            }
        }
    }
}

fun <T> List<T>.uniqueTriples(): Sequence<Triple<T, T, T>> = sequence {
    for (i in 0..lastIndex) {
        for (j in i + 1..lastIndex) {
            for (k in j + 1..lastIndex) {
                yield(Triple(get(i), get(j), get(k)))
            }
        }
    }
}

fun String.sorted(): String {
    return this.toList().sorted().joinToString(separator = "")
}

fun <T> List<T>.repeatIndefinitely() = generateSequence(0) {
    (it + 1) % this.size
}.map { this[it] }

fun <T> List<T>.permutations(): List<List<T>> {
    return if (this.size <= 1) {
        listOf(this)
    } else {
        val elementToInsert = first()
        drop(1).permutations().flatMap { permutation ->
            (0..permutation.size).map { i ->
                permutation.toMutableList().apply { add(i, elementToInsert) }
            }
        }
    }
}

fun <T, R> MutableMap<T, R>.copyOf(): MutableMap<T, R> {
    return mutableMapOf<T, R>().also { it.putAll(this) }
}

fun <T> MutableList<T>.copyOf(): MutableList<T> {
    return mutableListOf<T>().also { it.addAll(this) }
}

fun <T : Comparable<T>> Iterable<T>.min(): T {
    return requireNotNull(minOrNull()) { "No min value" }
}

fun <T : Comparable<T>> Iterable<T>.max(): T {
    return requireNotNull(maxOrNull()) { "No max value" }
}

fun <T : Comparable<T>> Iterable<T>.minMax(): List<T> {
    return listOf(min(), max())
}

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum: Long = 0
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun <T> printAndTest(value: T, expected: T) {
    println(value)
    require(value == expected)
}

fun <T> Array<Array<T>>.getOrNull(i: Int, j: Int): T? {
    return getOrNull(i)?.getOrNull(j)
}

fun <T> Array<Array<T>>.getOrNull(p: Point2D): T? {
    return getOrNull(p.y, p.x)
}

fun <T> Array<Array<T>>.set(i: Int, j: Int, value: T): Boolean {
    val a = getOrNull(i) ?: return false
    if (j in a.indices) {
        a[j] = value
        return true
    }
    return false
}

fun <T> Array<Array<T>>.positionOfFirst(predicate: (T) -> Boolean): Point2D? {
    forEachIndexed { y, arr ->
        arr.forEachIndexed { x, t ->
            if (predicate(t)) return Point2D(x, y)
        }
    }
    return null
}

operator fun <T> Array<Array<T>>.set(p: Point2D, value: T): Boolean {
    return set(p.y, p.x, value)
}

inline fun <reified T> Array<Array<T>>.deepCopy(): Array<Array<T>> {
    return Array(this.size) {
        this[it].copyOf()
    }
}

fun <T> Array<Array<T>>.positions() = sequence {
    val arr = this@positions
    for (y in arr.indices) {
        for (x in arr.first().indices) {
            yield(Point2D(x, y))
        }
    }
}

fun Long.setBit(index: Int): Long {
    return this or (1L shl index)
}

fun Long.unsetBit(index: Int): Long {
    return this and (1L shl index).inv()
}

fun Iterable<Int>.product(): Long {
    if (!this.any()) return 0
    var product = 1L
    for (element in this) {
        product *= element
    }
    return product
}

@JvmName("productLong")
fun Iterable<Long>.product(): Long {
    if (!this.any()) return 0
    var product = 1L
    for (element in this) {
        product *= element
    }
    return product
}

fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    if (!this.any()) return 0
    var product = 1L
    for (element in this) {
        product *= selector(element)
    }
    return product
}

fun <K, V> Map<K, V>.getOrErr(key: K): V {
    return get(key) ?: error("Key $key is not in map")
}

fun Sequence<Int>.product(): Long {
    var product = 1L
    for (element in this) {
        product *= element
    }
    return product
}

@JvmName("productLong")
fun Sequence<Long>.product(): Long {
    var product = 1L
    for (element in this) {
        product *= element
    }
    return product
}

fun <K, V> Map<K, V>.sumOf(mapper: (Map.Entry<K, V>) -> Int): Int {
    var sum = 0
    forEach { sum += mapper(it) }
    return sum
}

fun <T> Array<T>.getCyclic(index: Int): T {
    return get(index % size)
}

fun <T> Array<Array<T>>.getCyclic(row: Int, col: Int): T {
    return getCyclic(row).getCyclic(col)
}

fun <T> Array<T>.setCyclic(index: Int, value: T) {
    set(index % size, value)
}

fun <T> Array<Array<T>>.setCyclic(row: Int, col: Int, value: T) {
    getCyclic(row).setCyclic(col, value)
}

fun <T> Array<Array<T>>.forEachIndexed(
    action: (x: Int, y: Int, item: T) -> Unit,
) {
    forEachIndexed { y, items ->
        items.forEachIndexed { x, item ->
            action(x, y, item)
        }
    }
}

fun <T> List<T>.getCyclic(index: Int): T {
    return if (index < 0) {
        get((size + (index % size)) % size)
    } else get(index % size)
}

fun String.splitOnWhitespace(): List<String> {
    return this.split("\\s+".toPattern())
}

fun gcd(a: Long, b: Long): Long {
    var n1 = a.absoluteValue
    var n2 = b.absoluteValue
    while (n1 != n2) {
        if (n1 > n2)
            n1 -= n2
        else
            n2 -= n1
    }
    return n1
}

fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

fun lcm(c: Iterable<Long>): Long {
    return c.reduce { acc, l -> lcm(acc, l) }
}

fun <T> Collection<T>.split(condition: (T) -> Boolean): List<List<T>> {
    val collection = this
    val currentList = mutableListOf<T>()
    return buildList {
        for (item in collection) {
            if (condition(item)) {
                add(currentList.toList())
                currentList.clear()
            } else {
                currentList += item
            }
        }
        add(currentList.toList())
    }
}

fun intRangeOf(a: Int, b: Int): IntRange {
    return if (a < b) a..b else b..a
}

fun <T> List<List<T>>.indices(): Sequence<Point2D> = sequence {
    for (y in indices) {
        for (x in get(y).indices) {
            yield(Point2D(x, y))
        }
    }
}

operator fun <T> List<List<T>>.get(point: Point2D): T = get(point.y).get(point.x)
fun <T> List<List<T>>.getOrNull(point: Point2D): T? = getOrNull(point.y)?.getOrNull(point.x)

inline fun Long.digitCount(): Int {
    if (this == 0L) return 1
    var n = this.absoluteValue
    var digitCount = 0
    while (n > 0) {
        n /= 10
        digitCount++
    }
    return digitCount
}
