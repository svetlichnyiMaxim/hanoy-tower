import kotlin.system.measureTimeMillis

fun main() {
    Main().start()
}

var occurredPositions: MutableMap<String, MutableSet<Position>> = mutableMapOf()


val examplePos: Position = Position(
    2, mutableListOf(
        Line(byteArrayOf(0, 0, 0, 4, 5, 6, 7, 8)),
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(0, 0, 0, 0, 0, 1, 2, 3))
    )
)

class Main {
    fun start() {
        println("Enter search depth")
        var chosenDepth = readln().toInt()
        println("Enter max possible search depth")
        val maxDepth = readln().toInt()
        while (!runProgram(chosenDepth) && chosenDepth < maxDepth) {
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
