package br.ufrpe

fun main() {
    val test = Array(5) { Array(5) { 1 } }
    println(test)
    val copyTest = test.copyElements()
    println(copyTest)
    copyTest[0][1] = 0
    println("copyTest = ${copyTest.joinToString(" ")}")
    println("test = ${test.joinToString(" ")}")
}