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
    return when(this) {
        0 -> "a ->"
        1 -> "b ->"
        2 -> "c ->"
        3 -> "d ->"
        4 -> "e ->"
        else -> " "
    }
}

fun choosePath(graph: Array<Array<Int>>, path: String, actualVertex: Int): String {
    var newPath = path
    for(i in graph[actualVertex].indices) {
        newPath += graph[actualVertex][i].toString()
        
    }
}

fun generatePaths(graph: Array<Array<Int>>): String {
    var currentLetter: Char = 'a'
    var paths = mutableListOf<String>()
    for(i in graph.indices) {
        val tempCopy = graph.copyOf()
        var path = ""
        path += i.toVertex()
        var pathCost = 0
        // fazer com que ele não volte para a letra que ele começou
        tempCopy.forEach { it[i] = 0 }
        for(j in graph[i].indices) {
            val distance = graph[i][j]
            if(distance == 0) continue
            path += distance.toString() + j.toVertex()
            pathCost += distance

        }
    }
}