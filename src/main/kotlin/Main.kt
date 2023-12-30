import kotlin.math.max
import kotlin.system.measureTimeMillis

/**
 * steps we use to solve position
 */
var stepsToSolve: Int = 1

/**
 * total number of the disks
 */
const val disks: Int = 8

/**
 * part where the program starts
 */
fun main() {
    var solveResult: MutableList<Position>
    val time = measureTimeMillis {
        solveResult = examplePos.generateMoves(stepsToSolve)
        while (solveResult.isEmpty()) {
            stepsToSolve++
            println(stepsToSolve)
            solveResult = examplePos.generateMoves(stepsToSolve)
        }
    }
    solveResult.forEach {
        it.display()
        println("*".repeat(stepsToSolve))
    }
    println("solved in $time ms and ${max(solveResult.size - 1, 0)} steps")
}

/**
 * we store occurred positions here which massively increases speed
 */
val occurredPositions: HashMap<String, Pair<MutableCollection<Position>, Int>> = hashMapOf()


/**
 * an example pos created for testing
 */
val examplePos: Position = Position(
    3, mutableListOf(
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
        Line(byteArrayOf(8, 7, 6, 5, 4, 3, 2, 1))
    )
)