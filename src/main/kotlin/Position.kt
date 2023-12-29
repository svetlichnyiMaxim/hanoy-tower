/**
 * used to store position
 * @param startLine line we started from
 * @param lines all lines
 */
class Position(val startLine: Int, val lines: MutableList<Line>) {
    override fun equals(other: Any?): Boolean {
        if (other is Position) {
            if (this.startLine == other.startLine) {
                this.lines.forEachIndexed { index, line ->
                    if (line != other.lines[index]) {
                        return false
                    }
                }
            }
            return true
        } else {
            return super.equals(other)
        }
    }

    private fun toLong(): Long {
        return lines[0].hash * 1_000_000_000_000_000_000 + lines[1].hash * 1_000_000_000 + lines[2].hash
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
        return when (startLine) {
            0 -> {
                lines[1].isFull() || lines[2].isFull()
            }

            1 -> {
                lines[0].isFull() || lines[2].isFull()
            }

            else -> {
                lines[0].isFull() || lines[1].isFull()
            }
        }
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
                if (endIndex != startIndex && endElement.topElement() > startElement.topElement()) {
                    list.add(Move(startIndex, endIndex))
                }
            }
        }
        return list
    }

    /**
     * generates move from position
     */
    fun generateMoves(
        depth: Int,
        originalDepth: Int = depth
    ): MutableList<Pair<Position, Int>> {
        val currentDepth = originalDepth - depth + 1
        // no need to continue investigation if we can't improve depth score
        if (depth == 1) {
            return mutableListOf(Pair(generateMoves().firstOrNull { it.hasWon() }
                ?: return mutableListOf(), currentDepth + 1), Pair(this, currentDepth))
        }
        generateMoves().forEach { pos ->
            if (pos.hasWon()) {
                return mutableListOf(Pair(pos, currentDepth))
            }
            // Adds all moving sequences
            val list = pos.generateMoves(depth - 1, originalDepth)
            if (list.isNotEmpty()) {
                list.add(Pair(this, currentDepth))
                return list
            }
        }
        // fallback option
        return mutableListOf()
    }

    /**
     * @return all possible positions we can get after a move
     */
    private fun generateMoves(): MutableSet<Position> {
        val str = this.toLong()
        // if this pos was stored, we can use it's cached version, saves a lot of time
        occurredPositions[str]?.let {
            return it
        }
        val generatedList = mutableSetOf<Position>()
        possibleMove().forEach {
            generatedList.add(applyMove(it))
        }
        // Stores positions with it's generatedMoves
        occurredPositions[str] = generatedList
        return generatedList
    }

    /**
     * @param move the move we made
     * @return position after a curtain move
     */
    private fun applyMove(move: Move): Position {
        val lineToRemove = move.startLine
        val lineToAdd = move.endLine
        val elementToMove = this.lines[lineToRemove].topElement()
        val copy = lines.toMutableList()
        copy[lineToRemove] = copy[lineToRemove].removeTopElement()
        copy[lineToAdd] = copy[lineToAdd].addElement(elementToMove)
        return Position(startLine, copy)
    }
}

/**
 * it is used to store move - related info
 * @param startLine line we move the piece from
 * @param endLine line we move the piece to
 */
class Move(var startLine: Int, var endLine: Int)