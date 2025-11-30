package utils

import java.util.LinkedList
import java.util.Queue

fun <Node> bfs(
    start: Node,
    isEnd: (Node) -> Boolean,
    next: (Node) -> List<Node>,
): List<Node>? {
    val prev = mutableMapOf<Node, Node>()
    val q: Queue<Node> = LinkedList()
    q += start

    while (q.isNotEmpty()) {
        val c = q.poll()!!
        if (isEnd(c)) return createPath(start, c, prev)
        for (n in next(c)) {
            if (n !in prev) {
                prev[n] = c
                q += n
            }
        }
    }

    return null
}

private fun <Node> createPath(start: Node, end: Node, prev: Map<Node, Node>): List<Node>? {
    val l = mutableListOf(end)
    var c = prev[end]
    while (c != null) {
        l += c
        if (c == start) return l.reversed()
        c = prev[c]
    }
    return null
}
