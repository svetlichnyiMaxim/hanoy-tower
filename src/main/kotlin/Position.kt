/**
 * used to store position
 * @param startLine line we started from
 * @param lines all lines
 */
class Position(var startLine: Int, var lines: MutableList<Line>) {
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

    override fun toString(): String {
        return lines[0].toString() + "|" + lines[1].toString() + "|" + lines[2].toString()
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
    ): MutableCollection<MutableList<Pair<Position, Int>>> {
        val currentDepth = originalDepth - depth + 1
        // no need to continue investigation if we can't improve depth score
        if (depth == 1) {
            generateMoves().forEach {
                if (it.hasWon()) {
                    return mutableListOf(mutableListOf(Pair(it, currentDepth)))
                }
            }
            // if we weren't able to find a solution, what's the point of returning it?
            return mutableListOf()
        }
        val list = mutableListOf<MutableList<Pair<Position, Int>>>()
        generateMoves().forEach { pos ->
            if (pos.hasWon()) {
                return mutableListOf(mutableListOf(Pair(pos, currentDepth)))
            }
            // Adds all moving sequences
            list.addAll(pos.generateMoves(depth - 1, originalDepth).filter { it.isNotEmpty() })
        }
        if (list.any { it.isNotEmpty() }) {
            // we sort the list according to the final depth
            return mutableListOf((list.minBy { it.first().second } + Pair(this, -1)).toMutableList())
        }
        // fallback option
        return mutableListOf()
    }

    /**
     * @return all possible positions we can get after a move
     */
    fun generateMoves(): MutableSet<Position> {
        val str = this.toString()
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