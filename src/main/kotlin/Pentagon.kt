package br.ufrpe

/** a -> 0, b -> 1, c -> 2, d -> 3, e -> 4
 * graph[0] -> [a -> a, a -> b, a -> c, a -> d, a -> e]
 * 0 is for represent when you cannot use this path
 * */
val graph = arrayOf(
    arrayOf<Int>(0, 3, 8, 4, 7),
    arrayOf<Int>(3, 0, 10, 9, 2),
    arrayOf<Int>(8, 10, 0, 6, 5),
    arrayOf<Int>(4, 9, 6, 0, 1),
    arrayOf<Int>(7, 2, 5, 1, 0)
)


//val graphFour = mapOf(
//    'a' to mapOf('b' to 3, 'c' to 8, 'd' to 4),
//    'b' to mapOf('a' to 3, 'c' to 10, 'd' to 9),
//    'c' to mapOf('a' to 8, 'b' to )
//)

fun Int.toVertex(): String {
    return when (this) {
        0 -> "a ->"
        1 -> "b ->"
        2 -> "c ->"
        3 -> "d ->"
        4 -> "e ->"
        else -> " "
    }
}

//fun choosePath(graph: Array<Array<Int>>, path: String, actualVertex: Int): String {
//    var newPath = path
//    for(i in graph[actualVertex].indices) {
//        newPath += graph[actualVertex][i].toString()
//
//    }
//}

fun generatePaths(graph: Array<Array<Int>>): String {
    var paths = mutableListOf<String>()
    for (i in graph.indices) {
        var tempCopy1 = graph.copyOf()
        var path = ""
        path += i.toVertex()
//        var pathCost = 0
        // fazer com que ele não volte para a letra que ele começou
        tempCopy1.forEach { it[i] = 0 }

        for (j in tempCopy1[i].indices) {
            val distance = tempCopy1[i][j]
            if (distance == 0) continue
            path += distance.toString() + j.toVertex()
            tempCopy1.forEach { it[j] = 0 }

            for (k in tempCopy1[j].indices) {
                val distance = tempCopy1[j][k]
                if (distance == 0) continue
                path += distance.toString() + k.toVertex()
                tempCopy1.forEach { it[k] = 0}

                for (l in tempCopy1[k].indices) {
                    val distance = tempCopy1[k][l]
                    if (distance == 0) continue
                    path += distance.toString() + l.toVertex()
                    tempCopy1.forEach { it[l] = 0 }

                    for(m in tempCopy1[l].indices) {
                        val distance = tempCopy1[l][m]
                        if(distance == 0) continue
                        path += distance.toString() + m.toVertex()
                        tempCopy1.forEach { it[m] = 0 }

                        // come back for when start
                        val finalDistance = graph[m][i]
                        path += finalDistance.toString() + m.toVertex()
                        paths.add(path)
                        tempCopy1 = graph.copyOf()
                    }
                }
            }
        }
//            pathCost += distance

    }
    return paths.joinToString("")
}
