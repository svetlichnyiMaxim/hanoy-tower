import kotlin.system.measureTimeMillis

/**
 * used to store position
 * @param startLine line we started from
 * @param lines all lines
 */
class Position(private val startLine: Int, private val lines: Array<Line>, private val maxSize: Int) {
    private fun hash(): String {
        return lines.joinToString(separator = " ") { it.hash }
    }

    /**
     * displays position in a human-readable form
     */
    override fun toString(): String {
        return lines.joinToString(
            separator = "\n"
        ) {
            it.toString()
        }
    }

    /**
     * @return if the game has ended
     */
    private fun hasWon(): Boolean {
        lines.forEachIndexed { index, line ->
            if (index != startLine && line.size() == maxSize) return true
        }
        return false
    }

    /**
     * @return possible moves that can occur
     */
    private fun possibleMoves(): MutableList<Move> {
        val list = mutableListOf<Move>()
        lines.forEachIndexed { startLineIndex, startLine ->
            if (startLine.empty()) {
                return@forEachIndexed
            }
            lines.forEachIndexed { endLineIndex, endLine ->
                if (endLineIndex != startLineIndex && (endLine.empty() || endLine.peek() > startLine.peek())) {
                    list.add(Move(startLineIndex, endLineIndex))
                }
            }
        }
        return list
    }

    /**
     * solves position and measures used time
     * @return Pair of solve sequence and time used
     */
    fun solveRecursivelyWithTimeMeasurement(): Pair<MutableList<Position>, Long> {
        var solveResult: MutableList<Position>
        val time = measureTimeMillis {
            solveResult = solveRecursively()
        }
        return Pair(solveResult, time)
    }

    /**
     * solves position
     * @return winning sequence
     */
    fun solveRecursively(): MutableList<Position> {
        var currentDepth = 0
        var solveResult = solveAtDepth(currentDepth)
        while (solveResult.isEmpty()) {
            currentDepth++
            solveResult = solveAtDepth(currentDepth)
        }
        return solveResult
    }

    /**
     * finds position solution or nothing
     * @return winning sequence or empty list
     */
    fun solveAtDepth(
        depth: Int
    ): MutableList<Position> {
        if (this.hasWon()) {
            return mutableListOf(this)
        }
        if (depth == 0) {
            return mutableListOf()
        }
        // I love clean code
        return (generateMoves(depth).map {
            it.solveAtDepth(depth - 1)
        }.filter {
            it.isNotEmpty()
        }.minByOrNull {
            it.size
        } ?: return mutableListOf()).apply {
            this.add(this@Position)
        }
    }

    /**
     * @return set of all possible positions we can get after a move
     */
    private fun generateMoves(depth: Int): Collection<Position> {
        val hashCode = hash()
        // if this pos was stored, we can use it's cached version, saves a lot of time
        occurredPositions[hashCode]?.let {
            if (it.second >= depth) {
                return mutableSetOf()
            } else {
                occurredPositions[hashCode] = Pair(it.first, depth)
                return it.first
            }
        }
        val generatedList = this.possibleMoves().map {
            this.applyMove(it)
        }
        // stores position with it's generatedMoves in the hashMap
        occurredPositions[hashCode] = Pair(generatedList, depth)
        return generatedList
    }

    /**
     * @param move the move we made
     * @return position after a curtain move
     */
    private fun applyMove(move: Move): Position {
        val copy = Array(lines.size) { lines[it].copy() }
        val elementToMove = copy[move.startLine].peek()
        copy[move.startLine].pop()
        copy[move.endLine].push(elementToMove)
        return Position(startLine, copy, maxSize)
    }
}

/**
 * it is used to store move - related info
 * @param startLine line we move the piece from
 * @param endLine line we move the piece to
 */
class Move(var startLine: Int, var endLine: Int)
