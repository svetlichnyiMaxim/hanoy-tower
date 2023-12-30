import kotlin.system.measureTimeMillis

/**
 * steps we use to solve position
 */
var stepsToSolve: Int = 255

/**
 * part where the program starts
 */
fun main() {
    var solveResult: MutableList<Pair<Position, Int>>
    val time = measureTimeMillis {
        solveResult = examplePos.generateMoves(stepsToSolve)
        while (solveResult.isEmpty()) {
            stepsToSolve++
            println(stepsToSolve)
            solveResult = examplePos.generateMoves(stepsToSolve)
        }
    }
    solveResult.forEach {
        it.first.display()
        println("*".repeat(stepsToSolve))
    }
    println("solved in $time ms and ${solveResult.size} steps")
}

/**
 * we store occurred positions here which massively increases speed
 */
val occurredPositions: HashMap<Long, Pair<MutableSet<Position>, Int>> = hashMapOf()


/**
 * an example pos created for testing
 */
val examplePos: Position = Position(
    2, mutableListOf(
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(8, 7, 6, 5, 4, 3, 2, 1))
    )
)