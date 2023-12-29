import kotlin.math.pow

/**
 * provides a way to store line data, etc.
 * new elements are stored at the start of the array
 * @param elements elements in the array
 * @param topOneIndex stores top index of the line
 */
class Line(private var elements: ByteArray, private var topOneIndex: Int, var hash: Long) {
    constructor(elements: ByteArray) : this(elements, elements.indexOfFirst { it == 0.toByte() }, elements.toLong())

    /**
     * used in auto tests for proper comparison
     */
    override fun equals(other: Any?): Boolean {
        if (other is Line) {
            val b = elements.contentEquals(other.elements)
            return b
        }
        return super.equals(other)
    }


    /**
     * checks if line is full
     * @return if the line is full
     */
    fun isFull(): Boolean {
        return topOneIndex == 8
    }

    /**
     * @return copy of the line with a removed top element
     */
    fun removeTopElement(): Line {
        val hashValue = hash - elements[topOneIndex - 1] * (10f.pow(8 - topOneIndex)).toInt()
        return Line(
            elements.copyOf().also { it[topOneIndex - 1] = 0 }, topOneIndex - 1, hashValue
        )
    }

    /**
     * @return copy of the line with the specific element added to the top
     */
    fun addElement(element: Byte): Line {
        val hashValue = hash + element * (10f.pow(7 - topOneIndex)).toInt()
        return Line(
            elements.copyOf().apply { this[topOneIndex] = element }, topOneIndex + 1, hashValue
        )
    }

    /**
     * used to determine if the line is empty
     */
    fun empty(): Boolean {
        return topOneIndex == 0
    }

    /**
     * @return topElement if exists, 9 if line is full
     */
    fun topElement(): Byte {
        return elements.lastOrNull { it != 0.toByte() } ?: 9
    }

    /**
     * displays line in a human-readable form
     */
    fun display() {
        elements.forEach { if (it != 0.toByte()) print("$it ") else print("  ") }
    }
}

fun ByteArray.toLong(): Long {
    var stringBuilder = 0L
    this.forEach {
        stringBuilder *= 10
        stringBuilder += it
    }
    return stringBuilder
}