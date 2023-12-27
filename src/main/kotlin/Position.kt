class Position(private var startLine: Int, private var pos: MutableList<Line>) {

    fun startSolving(depth: Int): MutableList<MutableList<Pair<Position, Int>>> {
        return generateMoves(depth, depth)
    }

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

    private fun generateMoves(depth: Int, originalDepth: Int): MutableList<MutableList<Pair<Position, Int>>> {
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
        return mutableListOf(
        )
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