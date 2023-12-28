class Line(private var elements: ByteArray, var topOneIndex: Int = -1) {
    init {
        if (topOneIndex == -1) {
            topOneIndex = topOneIndex()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is Line) {
            val b = this.elements.contentEquals(other.elements)
            return b
        }
        return super.equals(other)
    }

    /*
    This print line in human-readable form
     */
    override fun toString(): String {
        var stringBuilder = ""
        elements.forEach { stringBuilder += it }
        return stringBuilder
    }

    fun isFull(): Boolean {
        return elements.last() != 0.toByte()
    }

    fun removeTopElement(): Line {
        val copy = this.elements.copyOf()
        copy[topOneIndex - 1] = 0
        return Line(copy, topOneIndex - 1)
    }

    fun addElement(element: Byte): Line {
        // we don't check if there is any space left, cause there are n elements and size of tower we want to move
        // and element to move is n-1 at max
        val copy = this.elements.copyOf()
        copy[topOneIndex] = element
        return Line(copy, this.topOneIndex + 1)
    }

    fun isEmpty(): Boolean {
        return topOneIndex == 0
    }

    // 2 1 0 0...
    fun topOne(): Byte? {
        return elements.lastOrNull { it != 0.toByte() }
    }

    private fun topOneIndex(): Int {
        return elements.indexOfFirst { it == 0.toByte() }
    }

    fun display() {
        elements.forEach { if (it != 0.toByte()) print("$it ") else print("  ") }
    }
}