import kotlin.math.min

data class Position(var startLine: Int, var pos: MutableList<Line>) {

    fun startSolving(depth: Int): MutableSet<Position> = generateMoves(depth, depth)

    override fun toString(): String {
        var str = startLine.toString()
        pos.forEach {
            str += it.toString()
        }
        return str
    }

    fun display() {
        pos.forEach {
            it.display()
            println()
        }
    }

    private fun hasWon(): Boolean {
        pos.forEachIndexed { index, line ->
            if (index != startLine && line.isFull()) {
                return true
            }
        }
        return false
    }

    private fun possibleMove(): MutableSet<Pair<Int, Int>> {
        val list = mutableSetOf<Pair<Int, Int>>()
        pos.forEachIndexed { index1, it1 ->
            if (!it1.isEmpty()) pos.forEachIndexed { index, it ->
                if (it.topOne() > it1.topOne() || it.isEmpty()) {
                    list.add(Pair(index1, index))
                }
            }
        }
        return list
    }

    private tailrec fun generateMoves(depth: Int, originalDepth: Int): MutableSet<Position> {
        if (depth == 1) {
            generateMoves().forEach {
                if (it.hasWon()) {
                    solvedStep = min(originalDepth - depth + 1, solvedStep)
                    isSolved = true
                    return mutableSetOf(it)
                }
            }
            // if we weren't able to find a solution, what's the point of returning it?
            return mutableSetOf()
        }

        val list = mutableSetOf<Position>()
        generateMoves().forEach {
            if (it.hasWon()) {
                solvedStep = min(originalDepth - depth + 1, solvedStep)
                isSolved = true
                return mutableSetOf(it)
            }
            it.generateMoves(depth - 1, originalDepth).forEach { it1 ->
                list.add(it1)
            }
        }
        return if (list.isNotEmpty()) mutableSetOf(list.first())
        else list
    }

    private fun generateMoves(): MutableSet<Position> {
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

    private fun applyMove(move: Pair<Int, Int>): Position {
        val lineToRemove = move.first
        val lineToAdd = move.second
        val elementToMove = this.pos[lineToRemove].topOne()
        val copy = pos.toMutableList()
        copy[lineToRemove] = copy[lineToRemove].removeTopElement()
        copy[lineToAdd] = copy[lineToAdd].addElement(elementToMove)
        return Position(startLine, copy)
    }
}