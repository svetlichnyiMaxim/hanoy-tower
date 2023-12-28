/**
 * provides a way to store line data, etc.
 * new elements are stored at the start of the array
 * @param elements elements in the array
 * @param topOneIndex stores top index of the line
 */
class Line(private var elements: ByteArray, private var topOneIndex: Int = -1) {
    init {
        if (topOneIndex == -1) {
            topOneIndex = topIndex()
        }
    }

    /**
     * used in auto tests for proper comparison
     */
    override fun equals(other: Any?): Boolean {
        if (other is Line) {
            val b = this.elements.contentEquals(other.elements)
            return b
        }
        return super.equals(other)
    }

    /**
     * prints line data in human-readable form
     */
    override fun toString(): String {
        var stringBuilder = ""
        elements.forEach { stringBuilder += it }
        return stringBuilder
    }

    /**
     * checks if line is full
     * @return if the line is full
     */
    fun isFull(): Boolean {
        return elements.last() != 0.toByte()
    }

    /**
     * @return copy of the line with a removed top element
     */
    fun removeTopElement(): Line {
        val copy = this.elements.copyOf()
        copy[topOneIndex - 1] = 0
        return Line(copy, topOneIndex - 1)
    }

    /**
     * @return copy of the line with the specific element added to the top
     */
    fun addElement(element: Byte): Line {
        val copy = this.elements.copyOf().apply { this[topOneIndex] = element }
        return Line(copy, this.topOneIndex + 1)
    }

    /**
     * used to determine if the line is empty
     */
    fun isEmpty(): Boolean {
        return topOneIndex == 0
    }

    /**
     * @return topElement if exists, 9 if line is full
     */
    fun topElement(): Byte {
        return elements.lastOrNull { it != 0.toByte() } ?: 9
    }

    private fun topIndex(): Int {
        return elements.indexOfFirst { it == 0.toByte() }
    }

    /**
     * displays line in a human-readable form
     */
    fun display() {
        elements.forEach { if (it != 0.toByte()) print("$it ") else print("  ") }
    }
}