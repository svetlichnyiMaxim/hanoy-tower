import kotlin.system.measureTimeMillis

/**
 * part where the program starts
 */
fun main() {
    val chosenDepth = 15
    runProgram(chosenDepth)
}

/**
 * we store occurred positions here which massively increases speed
 */
var occurredPositions: HashMap<Long, MutableSet<Position>> = hashMapOf()


/**
 * an example pos created for testing
 */
val examplePos: Position = Position(
    2, mutableListOf(
        Line(byteArrayOf(8, 7, 6, 5, 0, 0, 0, 0)),
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(4, 3, 2, 1, 0, 0, 0, 0))
    )
)

private fun runProgram(currentDepth: Int): Boolean {
    var solved = false
    println(
        "Finished time measuring, it took ${
            measureTimeMillis {
                val solveResult = examplePos.generateMoves(currentDepth)
                if (solveResult.isNotEmpty() && solveResult.first().isNotEmpty()) {
                    require(solveResult.size in 0..1)
                    println("It was solved in ${solveResult.first().size} steps")
                    solveResult.forEach {
                        it.forEach { (first, _) ->
                            println("*".repeat(15))
                            first.display()
                        }
                    }
                    println("*".repeat(15))
                    solved = true
                } else {
                    println("It wasn't solved yet, trying with bigger depth ${currentDepth + 1}")
                }
            }
        } ms")
    return solved
}