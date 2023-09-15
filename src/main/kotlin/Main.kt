const val version = 0.5
fun main(args: Array<String>) {
    println("Starting version $version")
    Main().start(args)
}

class Main {
    fun start(args: Array<String>) {
        val emptyLine = Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0))
        val emptyLine1 = Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0))
        val notEmptyLine = Line(mutableListOf(1, 2, 3, 4, 5, 6, 7, 8))
        val def = Position(
            0,
            mutableListOf(
                notEmptyLine, emptyLine.copy(), emptyLine1.copy()
            )
        )
        val def2 = Position(
            0,
            mutableListOf(
                notEmptyLine, emptyLine.copy(), emptyLine1.copy()
            )
        )
        println(def2.evaluation())
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
                        if (f < f1) {
                            list.add(Pair(index1, index))
                        }
                    }
                }
            }
            return list
        }

        fun generateMoves(depth: Int) {

        }

        fun generateMoves(): MutableList<Position> {
            val generatedList = mutableListOf<Position>()
            possibleMove().forEach {
                println(it)
                applyMove(it).display()
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

        fun addElement(element: Int): Line { // we don't check if there any space left, cause there are n elements and size of tower is n
            val copy = this.elements.toMutableList()
            val index1: Int = if (this.topOne() == 0) {
                elements.indexOf(this.topOne())
            } else {
                elements.indexOf(this.topOne()) + 1
            }
            copy[index1] = element
            return Line(copy)
        }

        fun topOne(): Int {
            // we use the fact, that they are always sorted like this 8, 5, 4, 1, 0...
            val index = elements.sorted().last()
            return index
        }

        fun copy(): Line {
            return Line(elements.toMutableList())
        }

        fun display() {
            elements.forEach { print("$it ") }
        }
    }
}