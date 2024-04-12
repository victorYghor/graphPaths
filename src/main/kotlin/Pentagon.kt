package br.ufrpe

typealias Graph = MutableMap<String,MutableMap<String, Int>>
typealias Entry = MutableMap.MutableEntry<String, MutableMap<String, Int>>
/** a -> 0, b -> 1, c -> 2, d -> 3, e -> 4
 * graph[0] -> [a -> a, a -> b, a -> c, a -> d, a -> e]
 * 0 is for represent when you cannot use this path
 * */
val graphPentagon = mutableMapOf(
    "a" to mutableMapOf("a" to 0, "b" to 3,"c" to 8, "d" to 4, "e" to 7),
    "b" to mutableMapOf("a" to 3, "b" to 0,"c" to 10, "d" to 9, "e" to 2),
    "c" to mutableMapOf("a" to 8, "b" to 10,"c" to 0, "d" to 6, "e" to 5),
    "d" to mutableMapOf("a" to 4, "b" to 9,"c" to 6, "d" to 0, "e" to 1),
    "e" to mutableMapOf("a" to 7, "b" to 2,"c" to 5, "d" to 1, "e" to 0),
)
fun Graph.removeLetter(letter: String): Graph {
    val graphCopy = this.copyOf()
    graphCopy.remove(letter)
    for(k in graphCopy.keys) {
        graphCopy[k]?.remove(letter) ?: println("k = $k is not a key of the graph ")
    }
    return graphCopy
}

fun findFinalPath(start: String, second: String, third: String, end: String, pastResult: String, originalGraph: Graph, cost: Int): Map<String, Int>{
    val startToSecond = originalGraph[start]!![second]!!
    val startToThird = originalGraph[start]!![third]!!
    val secondToThird = originalGraph[second]!![third]!!
    val thirdToSecond = originalGraph[second]!![third]!!
    val secondToEnd = originalGraph[second]!![end]!!
    val thirdToEnd = originalGraph[third]!![end]!!

    val sum1 = cost + startToSecond + secondToThird + thirdToEnd
    val sum2 = cost + startToThird + thirdToSecond + secondToEnd
    val path1 = pastResult + "${start + second} =" +
            " ${startToSecond} -> ${second + third} =" +
            " ${secondToThird} -> ${third + end} = ${thirdToEnd}" +
            " cost = $sum1"
    println(path1)
    val path2 = pastResult + "${start + third} =" +
            " ${startToThird} -> ${third + second} =" +
            " ${thirdToSecond} -> ${second + end} = ${secondToEnd}" +
            " cost = $sum2"
    println(path2)
    return mapOf(
        path1 to sum1,
        path2 to sum2
    )
}

fun generatePaths(
    graph: Graph,
    pastResult: String,
    startKey: String,
    firstStartKey: String,
    originalGraph: Graph,
    cost: Int
): Map<String, Int> {
    if(graph.size == 2) {
        return findFinalPath(
            start = startKey,
            second = graph.entries.first().key,
            third = graph.entries.last().key,
            end = firstStartKey,
            pastResult = pastResult,
            originalGraph = originalGraph.copyOf(),
            cost = cost
        )
    }else {
        // a ordem de remove letter não esta sendo feita no local correto
        // eu preciso do b aqui para que as outras coisas tenha informção do b tipo o quando o e for c o
        // entry do c precisa ter a informação da distancia para o b
        // ao final do programa quando ele chamar a função novamente o b não sera mais necessário e vai precisar ser removido ao final da chamada da função
        // past result precisa do key
        for(k in graph.keys) {
            val distance = originalGraph[k]!![startKey]!!
            return generatePaths(
                graph = graph.removeLetter(k),
                pastResult = pastResult + formatPath(distance.toString(), startKey + k),
                startKey = k,
                firstStartKey = firstStartKey,
                originalGraph = originalGraph.copyOf(),
                cost = distance + cost
            )
        }
    }
    return mapOf("" to Int.MAX_VALUE)
}

val graph = graphPentagon

fun main() {
    val pathsInfo = mutableMapOf<String, Int>()
    for(k in graphPentagon.keys) {
        val graphFirst = graphPentagon.removeLetter(k)
        for(j in graphFirst.keys) {
            val distance = graphPentagon[k]!![j]!!
            val result = generatePaths(
                graph = graphFirst.removeLetter(j),
                pastResult = "$k$j = ${distance} ->",
                startKey = j,
                firstStartKey = k,
                originalGraph = graphPentagon.copyOf(),
                cost = distance
            )
            // retirar os valores nulos da chamada da função
            if(result.entries.first().key != "") {
                pathsInfo.putAll(
                    result
                )
            }
        }
    }
    var min: MutableMap.MutableEntry<String, Int> = pathsInfo.entries.first()
    for(entry in pathsInfo.entries) {
        if(entry.value < min.value) {
            min = entry
        }
    }
    println("the min cost is: ${min.value} the path is ${min.key}")
}

fun MutableMap<String, Int>.copyOf(jvm: Boolean = false): MutableMap<String, Int> {
    val copy = mutableMapOf<String, Int>()
    for(k in this.keys) {
        copy[k] = this[k]!!
    }
    return copy
}

fun Graph.copyOf(): Graph {
    val copy = mutableMapOf<String, MutableMap<String, Int>>()
    for(k in this.keys) {
        copy[k] = this[k]!!.copyOf()
    }
    return copy
}
fun formatPath(distance: String, path: String) = "$path = $distance ->"

