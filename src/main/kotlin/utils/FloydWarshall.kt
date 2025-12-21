package utils

fun floydWarshall(
    graph: Array<Array<Int>>,
): Array<Array<Int>> {
    val distances = graph.deepCopy()
    val n = distances.size

    for (k in 0 until n) {
        for (i in 0 until n) {
            for (j in 0 until n) {
                distances[i][j] = minOf(distances[i][j], distances[i][k] + distances[k][j])
            }
        }
    }

    return distances
}
