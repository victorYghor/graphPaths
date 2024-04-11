package br.ufrpe

typealias Graph = MutableMap<String,MutableMap<String, Int>>
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

fun findFinalPath(start: MutableMap.MutableEntry<String, MutableMap<String, Int>>, second: MutableMap.MutableEntry<String, MutableMap<String, Int>>, third: MutableMap.MutableEntry<String, MutableMap<String, Int>>, graph: Graph, end: MutableMap.MutableEntry<String, MutableMap<String, Int>>, pastResult: String) {
    print(
        listOf(
        pastResult + "${start.key + second.key} =" +
            " ${graph[start.key]!![second.key]} -> ${second.key + third.key} =" +
                " ${graph[second.key]!![third.key]} -> ${third.key + end.key} = ${graph[second.key]!![end.key]}",

        pastResult + "${start.key + third.key} =" +
                " ${graph[start.key]!![third.key]} -> ${third.key + second.key} =" +
                " ${graph[third.key]!![second.key]} -> ${second.key + end.key} = ${graph[second.key]!![end.key]}"
        ).joinToString("\n")
    )
}

fun generatePaths(
    graph: Graph,
    pastResult: String,
    start: MutableMap.MutableEntry<String, MutableMap<String, Int>>,
    end: MutableMap.MutableEntry<String, MutableMap<String, Int>>
) {
    for(e in graph.entries) {
        generatePaths(
            graph = graph.removeLetter(e.key),
            pastResult = pastResult + formatPath(graph[e.key]!![start.key].toString(), start.key + e.key),
            start = e,
            end = end
        )
    }
    if(graph.size == 3) {
        // sera que first é igual a second ? não eu removo ele do map
        findFinalPath(
            graph = graph,
            start = start,
            second = graph.entries.first(),
            third = graph.entries.last(),
            end = end,
            pastResult = pastResult
        )
    }
}

val graph = graphPentagon

fun main() {

    // testando a funcionalidade de remover letras
//    println("remove 0" + graphPentagon.removeLetter(0))
//    println("remove 1" + graphPentagon.removeLetter(1))
//    println("remove 2" + graphPentagon.removeLetter(2))
//    println("remove 3" + graphPentagon.removeLetter(3))
//    println("remove 4" + graphPentagon.removeLetter(4))
}


fun Graph.copyOf(): Graph {
    val copied = mutableMapOf<String, MutableMap<String, Int>>()
    for(k in this.keys) {
        copied[k] = this[k]!!
    }
    return copied
}
fun formatPath(distance: String, path: String) = "$path = $distance ->"

