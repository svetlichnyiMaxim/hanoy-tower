import kotlin.system.measureTimeMillis

const val version: Double = 0.5
fun main() {
    println("Starting version $version")
    Main().start()
}

var isSolved: Boolean = false
var solvedStep: Int = Int.MAX_VALUE
var occurredPositions: HashMap<String, MutableSet<Position>> = HashMap()

class Main {
    fun start() {
        val examplePos = Position(
            2, mutableListOf(
                Line(mutableListOf(0, 0, 0, 0, 5, 6, 7, 8)),
                Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)),
                Line(mutableListOf(0, 0, 0, 0, 1, 2, 3, 4))
            )
        )
        println(
            "Finished time measuring ${
                measureTimeMillis {
                    examplePos.startSolving(15).forEach {
                        it.display()
                    }
                }
            } ms")
        if (isSolved) println("It was solved in $solvedStep steps")
        else {
            println("It wasn't solved yet, try with bigger depth")
        }
    }
}
