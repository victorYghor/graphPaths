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
        0 -> "a"
        1 -> "b"
        2 -> "c"
        3 -> "d"
        4 -> "e"
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

fun putZero(arr: Array<Array<Int>>, pos: Int) {
    for(i in arr.indices) {
        arr[i][pos] = 0
    }
}

fun Array<Array<Int>>.copyElements(): Array<Array<Int>> {
    val copied = Array(this.size) { Array(this.size) { 0 } }
    for(i in this.indices) {
        for(j in this[i].indices) {
            copied[i][j] = this[i][j]
        }
    }
    return copied
}

fun formatPath(distance: String, letter: String) = "$letter = $distance -> $letter"

fun generatePaths(graph: Array<Array<Int>>): String {
    var paths = mutableListOf<String>()
    for (i in graph.indices) {
        var tempCopy1 = graph.copyElements()
        var path = ""
        path += i.toVertex()
//        var pathCost = 0
        // fazer com que ele não volte para a letra que ele começou
        putZero(tempCopy1, i)

        for (j in tempCopy1[i].indices) {
            val tempCopy2 = tempCopy1.copyElements()
            val distance = tempCopy2[i][j]
            if (distance == 0) continue
            path += formatPath(distance.toString(), j.toVertex(),)
            putZero(tempCopy2, j)

            for (k in tempCopy2[j].indices) {
                val tempCopy3 = tempCopy2.copyElements()
                val distance = tempCopy3[j][k]
                if (distance == 0) continue
                path += formatPath(distance.toString(), k.toVertex())
                putZero(tempCopy3, k)

                for (l in tempCopy3[k].indices) {
                    val tempCopy4 = tempCopy3.copyElements()
                    val distance = tempCopy4[k][l]
                    if (distance == 0) continue
                    path += formatPath(distance.toString(), l.toVertex())
                    putZero(tempCopy4, l)

                    for(m in tempCopy4[l].indices) {
                        val distance = tempCopy4[l][m]
                        if(distance == 0) continue
                        path += formatPath(distance.toString(), m.toVertex())

                        // come back for when start
                        val finalDistance = graph[i][m]
                        path += "${i.toVertex()} = $finalDistance"
                        paths.add(path)
                        path = ""
                    }
                }
            }
        }
//            pathCost += distance
    }
    return paths.joinToString("\n")
}
