class Line(private var elements: MutableList<Byte>) {

    /*
    This print line in human-readable form
     */
    override fun toString(): String {
        var stringBuilder = ""
        elements.forEach {
            stringBuilder += it
        }
        return stringBuilder
    }

    fun isFull(): Boolean {
        return elements.first() != 0.toByte()
    }

    fun removeTopElement(): Line {
        val copy = this.elements.toMutableList()
        copy[this.topOneIndex()] = 0
        return Line(copy)
    }

    fun addElement(element: Int): Line {
        // we don't check if there is any space left, cause there are n elements and size of tower we want to move
        // an element to is n-1 at max
        val copy = this.elements.toMutableList()
        copy[this.topOneIndex() + if (this.topOne() == 0) 0 else -1] = element.toByte()
        return Line(copy)
    }

    fun isEmpty(): Boolean {
        return elements.last() == 0.toByte()
    }

    fun topOne(): Int {
        // we use the fact, that they are always sorted like this 0, 1, 2, 3, 4...
        elements.forEachIndexed { index, i ->
            if (i != 0.toByte() || index == 7)
                return i.toInt()
        }
        // This never happens
        throw IllegalStateException()
    }

    private fun topOneIndex(): Int {
        // we use the fact, that they are always sorted like this 0, 1, 2, 3, 4...
        elements.forEachIndexed { index, i ->
            if (i != 0.toByte() || index == 7)
                return index
        }
        // This never happens
        throw IllegalStateException()
    }

    fun display() {
        elements.forEach { print("$it ") }
    }
}