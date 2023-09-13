import org.jetbrains.annotations.TestOnly

class Main {
    fun main(args: Array<String>) {
    }

    data class Position(var pos: MutableList<Line>) {
        fun display() {
            pos.forEach {
                it.elements.forEach { it1 ->
                    print("$it1 ")
                }
                println()
            }
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
    }

    class Line(var elements: MutableList<Int>) {
        fun topOne(): Int {
            val index = elements.sortedBy { it }.last()
            return index
        }

        fun copy(): Line {
            return Line(elements.toMutableList())
        }
    }
}