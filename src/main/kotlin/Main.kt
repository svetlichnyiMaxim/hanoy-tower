import kotlin.system.measureTimeMillis

fun main() {
    println(examplePos.toString())
    Main().start()
}

var occurredPositions: MutableMap<String, MutableSet<Position>> = mutableMapOf()


val examplePos: Position = Position(
    2, mutableListOf(
        Line(byteArrayOf(8, 7, 6, 5, 0, 0, 0, 0)),
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(4, 3, 2, 1, 0, 0, 0, 0))
    )
)

class Main {
    fun start() {
        var chosenDepth = 1
        while (!runProgram(chosenDepth) && chosenDepth < 25) {
            chosenDepth += 2
        }
    }

    private fun runProgram(currentDepth: Int): Boolean {
        var solved = false
        println(
            "Finished time measuring, it took ${
                measureTimeMillis {
                    val solveResult = examplePos.startSolving(currentDepth)
                    if (solveResult.isNotEmpty() && solveResult.first().isNotEmpty()) {
                        println("It was solved in ${solveResult.first().first().second} steps")
                        solveResult.forEach {
                            it.forEach { (first, _) ->
                                println("*".repeat(15))
                                first.display()
                            }
                        }
                        println("*".repeat(15))
                        solved = true
                    } else {
                        println("It wasn't solved yet, trying with bigger depth ${currentDepth + 2}")
                    }
                }
            } ms")
        return solved
    }
}
