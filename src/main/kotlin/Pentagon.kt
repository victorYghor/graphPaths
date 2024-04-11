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

fun findFinalPath(start: String, second: String, third: String, end: String, pastResult: String, originalGraph: Graph) {
    print(
        listOf(
        pastResult + "${start + second} =" +
            " ${originalGraph[start]!![second]} -> ${second + third} =" +
                " ${originalGraph[second]!![third]} -> ${third + end} = ${originalGraph[second]!![end]}",

        pastResult + "${start + third} =" +
                " ${originalGraph[start]!![third]} -> ${third + second} =" +
                " ${originalGraph[third]!![second]} -> ${second + end} = ${originalGraph[second]!![end]}"
        ).joinToString("\n")
    )
}

fun generatePaths(
    graph: Graph,
    pastResult: String,
    startKey: String,
    firstStartKey: String,
    originalGraph: Graph
) {
    if(graph.size == 2) {
        findFinalPath(
            start = startKey,
            second = graph.entries.first().key,
            third = graph.entries.last().key,
            end = firstStartKey,
            pastResult = pastResult,
            originalGraph = originalGraph
        )
    }else {
        // a ordem de remove letter não esta sendo feita no local correto
        // eu preciso do b aqui para que as outras coisas tenha informção do b tipo o quando o e for c o
        // entry do c precisa ter a informação da distancia para o b
        // ao final do programa quando ele chamar a função novamente o b não sera mais necessário e vai precisar ser removido ao final da chamada da função
        // past result precisa do key
        for(k in graph.keys) {
            generatePaths(
                graph = graph.removeLetter(k),
                pastResult = pastResult + formatPath(originalGraph[k]!![startKey].toString(), startKey + k),
                startKey = k,
                firstStartKey = firstStartKey,
                originalGraph = originalGraph
            )
        }
    }
}

val graph = graphPentagon

fun main() {
    for(k in graphPentagon.keys) {
        val graphFirst = graphPentagon.removeLetter(k)
        for(j in graphFirst.keys) {
            generatePaths(
                graph = graphFirst.removeLetter(j),
                pastResult = "$k$j = ${graphPentagon[k]!![j]} ->",
                startKey = j,
                firstStartKey = k,
                originalGraph = graphPentagon
                )
        }
    }
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

