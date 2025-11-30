package utils

fun <Node> edmondsKarp(
    source: Node,
    sink: Node,
    edges: (Node) -> Collection<Node>,
    capacity: (Node, Node) -> Int,
): FlowResult<Node> {
    val edgeFlow = mutableMapOf<Pair<Node, Node>, Int>()
    fun getFlow(a: Node, b: Node): Int = edgeFlow[a to b] ?: 0
    fun incrementFlow(a: Node, b: Node, inc: Int) = edgeFlow.merge(a to b, inc, Int::plus)
    var flow = 0

    while (true) {
        val path = bfs(
            start = source,
            isEnd = { it == sink },
            next = { c ->
                val nxt = edges(c)
                nxt.filter { n ->
                    capacity(c, n) - getFlow(c, n) > 0
                }
            }
        ) ?: break
        val value = path.windowed(2).minOf { (a, b) ->
            capacity(a, b) - getFlow(a, b)
        }
        flow += value
        path.windowed(2).forEach { (a, b) ->
            incrementFlow(a, b, value)
            incrementFlow(b, a, -value)
        }
    }

    return FlowResult(flow, edgeFlow)
}

data class FlowResult<Node>(
    val totalFlow: Int,
    val edgeFlow: Map<Pair<Node, Node>, Int>,
)
