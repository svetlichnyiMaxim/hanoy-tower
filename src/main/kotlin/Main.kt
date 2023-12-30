import kotlin.math.max
import kotlin.system.measureTimeMillis

/**
 * steps we use to solve position
 */
var stepsToSolve: Int = 1

/**
 * total number of the disks
 */
const val DISKS: Int = 8

/**
 * part where the program starts
 */
fun main() {
    val solveResult = solve(examplePos)
    solveResult.first.forEach {
        it.display()
        println("*".repeat(stepsToSolve))
    }
    println("solved in ${solveResult.second} ms and ${max(solveResult.first.size - 1, 0)} steps")
}

/**
 * it solves positions xd
 */
fun solve(position: Position): Pair<MutableList<Position>, Long> {
    var solveResult: MutableList<Position>
    val time = measureTimeMillis {
        solveResult = position.generateMoves(stepsToSolve)
        while (solveResult.isEmpty()) {
            stepsToSolve++
            println(stepsToSolve)
            solveResult = position.generateMoves(stepsToSolve)
        }
    }
    return Pair(solveResult, time)
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
