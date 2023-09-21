import kotlin.system.measureTimeMillis

const val version: Double = 0.5
fun main() {
    println("Starting version $version")
    Main().start()
}

var occurredPositions: HashMap<String, MutableSet<Position>> = HashMap()

class Main {
    fun start() {
        val examplePos = Position(
            2, mutableListOf(
                Line(mutableListOf(0, 0, 0, 4, 5, 6, 7, 8)),
                Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)),
                Line(mutableListOf(0, 0, 0, 0, 0, 1, 2, 3))
            )
        )
        println(
            "Finished time measuring, it took ${
                measureTimeMillis {
                    val solveResult = examplePos.startSolving(16)
                    if (solveResult.first().isNotEmpty()) {
                        println("It was solved in ${solveResult.first().first().second} steps")
                        solveResult.forEach {
                            it.forEach { (first, _) ->
                                println("*".repeat(15))
                                first.display()
                            }
                        }
                        println("*".repeat(15))
                    } else {
                        println("It wasn't solved yet, try with bigger depth")
                    }
                }
            } ms")
    }
}
