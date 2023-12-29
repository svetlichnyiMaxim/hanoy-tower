import kotlin.system.measureTimeMillis

/**
 * part where the program starts
 */
fun main() {
    val solveResult: MutableList<Pair<Position, Int>>
    val time = measureTimeMillis {
        solveResult = examplePos.generateMoves(15)
    }
    solveResult.forEach {
        it.first.display()
        println("*".repeat(15))
    }
    println("solved in $time ms and ${solveResult.size} steps")
}

/**
 * we store occurred positions here which massively increases speed
 */
val occurredPositions: HashMap<Long, MutableSet<Position>> = hashMapOf()


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