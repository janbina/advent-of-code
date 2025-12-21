package utils

import java.util.*

fun <Node> aStarSearch(
    start: Node,
    isEnd: (Node) -> Boolean,
    next: (Node) -> Iterable<Pair<Node, Int>>,
    heuristicCostToEnd: (Node) -> Int,
    findLongest: Boolean = false,
): AStarResult<Node>? {

    val costFromStart: MutableMap<Node, Int> = mutableMapOf(start to 0)
    val estimatedTotalCost: MutableMap<Node, Int> = mutableMapOf(start to heuristicCostToEnd(start))
    val cameFrom: MutableMap<Node, Node> = mutableMapOf()
    val openVertices = PriorityQueue<Node>(
        when {
            findLongest -> compareByDescending { estimatedTotalCost[it] ?: 0 }
            else -> compareBy { estimatedTotalCost[it] ?: Int.MAX_VALUE }
        }
    )

    openVertices.add(start)

    while (openVertices.isNotEmpty()) {
        val current = openVertices.poll()
        if (isEnd(current)) {
            return AStarResult(
                path = generatePath(current, cameFrom),
                cost = costFromStart[current]!!
            )
        }
        next(current)
            .forEach { (neighbor, cost) ->
                val possibleNewCostToNeighbor = costFromStart[current]?.plus(cost) ?: Int.MAX_VALUE
                val currentCostToNeighbor = costFromStart[neighbor] ?: Int.MAX_VALUE
                if (possibleNewCostToNeighbor < currentCostToNeighbor) {
                    costFromStart[neighbor] = possibleNewCostToNeighbor
                    estimatedTotalCost[neighbor] =
                        possibleNewCostToNeighbor + heuristicCostToEnd(neighbor)
                    cameFrom[neighbor] = current
                    if (neighbor !in openVertices) openVertices.add(neighbor)
                }
            }
    }

    return null
}

fun <Node> generatePath(end: Node, cameFrom: Map<Node, Node>): List<Node> {
    return generateSequence(end) { cameFrom[it] }.toList().asReversed()
}

class AStarResult<Node>(
    val path: List<Node>,
    val cost: Int,
)
