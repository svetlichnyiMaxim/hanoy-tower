import kotlin.system.measureTimeMillis

const val version: Double = 0.5
fun main() {
    println("Starting version $version")
    Main().start()
}

var isSolved: Boolean = false
var solvedStep: Int = Int.MAX_VALUE
var occurredPositions: java.util.HashMap<String, MutableSet<Position>> = HashMap()

class Main {
    fun start() {
        val examplePos = Position(
            2, mutableListOf(
                Line(mutableListOf(0, 0, 0, 4, 5, 6, 7, 8)),
                Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)),
                Line(mutableListOf(0, 0, 0, 0, 0, 1, 2, 3))
            )
        )
        println(examplePos.toString())
        println(measureTimeMillis {
            examplePos.startSolving(14).forEach {
                it.display()
            }
        })
        println("Finished time measuring")
        if (isSolved) println("It was solved $solvedStep")
        else {
            println("Not yet")
        }
    }
}
