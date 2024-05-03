/**
 * used to store position
 * @param startLine line we started from
 * @param lines all lines
 */
class Position(private val startLine: Int, private val lines: Array<Line>) {
    private fun hash(): String {
        return lines.joinToString(separator = " ") { it.hash }
    }

    /**
     * displays position in a human-readable form
     */
    fun display() {
        lines.forEach {
            it.display()
            println()
        }
    }

    /**
     * @return if the game has ended
     */
    private fun hasWon(): Boolean {
        lines.forEachIndexed { index, line ->
            if (index != startLine && line.isFull())
                return true
        }
        return false
    }

    /**
     * @return possible moves that can occur
     */
    private fun possibleMove(): MutableSet<Move> {
        val list = mutableSetOf<Move>()
        lines.forEachIndexed { startIndex, startElement ->
            if (lines[startIndex].empty()) {
                return@forEachIndexed
            }
            lines.forEachIndexed { endIndex, endElement ->
                if (endIndex != startIndex && (endElement.empty() || endElement.peek() > startElement.peek())) {
                    list.add(Move(startIndex, endIndex))
                }
            }
        }
        return list
    }

    /**
     * generates move from position
     */
    fun solve(
        depth: Int
    ): MutableList<Position> {
        // no need to continue investigation if we can't improve depth score
        if (depth == 1) {
            return mutableListOf(generateCurrentMoves(depth).firstOrNull { it.hasWon() } ?: return mutableListOf(),
                this)
        }
        // I love clean code
        return (generateCurrentMoves(depth)
            .map { it.solve(depth - 1) }
            .filter { it.isNotEmpty() }
            .minByOrNull { it.size } ?: return mutableListOf())
            .apply { this.add(this@Position) }
    }

    /**
     * @return set of all possible positions we can get after a move
     */
    private fun generateCurrentMoves(depth: Int): Collection<Position> {
        val str = hash()
        // if this pos was stored, we can use it's cached version, saves a lot of time
        occurredPositions[str]?.let {
            if (it.second >= depth) {
                return mutableSetOf()
            } else {
                occurredPositions[str] = Pair(it.first, depth)
                return it.first
            }
        }
        val generatedList = possibleMove().map { applyMove(it) }.toMutableSet()
        // stores position with it's generatedMoves in the hashMap
        occurredPositions[str] = Pair(generatedList, depth)
        return generatedList
    }

    /**
     * @param move the move we made
     * @return position after a curtain move
     */
    private fun applyMove(move: Move): Position {
        val copy = lines.clone()
        val lineToRemove = copy[move.startLine]
        val lineToAdd = copy[move.endLine]
        val elementToMove = lineToRemove.peek()
        copy[move.startLine] = lineToRemove.removeTopElement()
        copy[move.endLine] = lineToAdd.addElement(elementToMove)
        return Position(startLine, copy)
    }
}

/**
 * it is used to store move - related info
 * @param startLine line we move the piece from
 * @param endLine line we move the piece to
 */
class Move(var startLine: Int, var endLine: Int)
