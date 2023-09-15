const val version = 0.5
fun main(args: Array<String>) {
    println("Starting version $version")
    Main().start(args)
}

class Main {
    fun start(args: Array<String>) {
        val emptyLine = Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0))
        val emptyLine1 = Line(mutableListOf(0, 0, 0, 0, 0, 0, 2, 1))
        val notEmptyLine = Line(mutableListOf(0, 0, 3, 4, 5, 6, 7, 8))
        val def2 = Position(
            1,
            mutableListOf(
                notEmptyLine, emptyLine.copy(), emptyLine1.copy()
            )
        )

        print(notEmptyLine.topOne())
        def2.generateMoves().forEach {
            it.display()
            println()
        }
        println("ENDED")
        def2.generateMoves(2).forEach {
            it.display()
            println()
        }
    }

    data class Position(var startLine: Int, var pos: MutableList<Line>) {
        fun display() {
            pos.forEach {
                it.display()
                println()
            }
        }

        fun evaluation(): Int {
            pos.forEachIndexed { index, line ->
                if (index != startLine) {
                    if (line.isFull()) {
                        return 100
                    }
                }
            }
            return 0
        }

        fun possibleMove(): MutableList<Pair<Int, Int>> {
            val list = mutableListOf<Pair<Int, Int>>()
            pos.forEachIndexed { index1, it1 ->
                pos.forEachIndexed { index, it ->
                    val f = it.topOne()
                    val f1 = it1.topOne()
                    if (it != it1) {
                        if (f > f1) {
                            list.add(Pair(index1, index))
                        }
                    }
                }
            }
            return list
        }

        fun generateMoves(depth: Int): MutableList<Position> {
            if (depth == 1) {
                val listToCheck = generateMoves()
                listToCheck.forEach {
                    if (it.evaluation() == 100) {
                        println("SOLVED")
                        return listToCheck
                    }
                }
                return listToCheck
            }
            var list = mutableListOf<Position>()
            generateMoves().forEach {
                if (it.evaluation() == 100) {
                    println("SOLVED")
                }
                it.generateMoves(depth - 1).forEach { it1 ->
                    list.add(it1)
                }
            }
            return list
        }

        fun generateMoves(): MutableList<Position> {
            val generatedList = mutableListOf<Position>()
            possibleMove().forEach {
                generatedList.add(applyMove(it))
            }
            return generatedList
        }

        fun applyMove(move: Pair<Int, Int>): Position {
            val lineToRemove = move.first
            val lineToAdd = move.second
            val elementToMove = this.pos[lineToRemove].topOne()
            val copy = pos.toMutableList()
            copy[lineToRemove] = copy[lineToRemove].removeTopElement()
            copy[lineToAdd] = copy[lineToAdd].addElement(elementToMove)
            return Position(startLine, copy)
        }
    }

    class Line(var elements: MutableList<Int>) {
        fun isFull(): Boolean {
            return !elements.any { it == 0 }
        }

        fun removeTopElement(): Line {
            val copy = this.elements.toMutableList()
            val index1 = elements.indexOf(this.topOne())
            copy[index1] = 0
            return Line(copy)
        }

        fun addElement(element: Int): Line {
            // we don't check if there any space left, cause there are n elements and size of tower is n
            val copy = this.elements.toMutableList()
            val index1: Int = if (this.topOne() == 0) {
                elements.indexOfLast { it == this.topOne() }
            } else {
                elements.indexOfLast { it == this.topOne() } - 1
            }
            copy[index1] = element
            return Line(copy)
        }

        fun topOne(): Int {
            // we use the fact, that they are always sorted like this 0, 1, 2, 3, 4...
            elements.forEachIndexed { index, i ->
                if (i != 0 || index == 7)
                    return i
            }
            // This never happens
            throw IllegalStateException()
        }

        fun copy(): Line {
            return Line(elements.toMutableList())
        }

        fun display() {
            elements.forEach { print("$it ") }
        }
    }
}
