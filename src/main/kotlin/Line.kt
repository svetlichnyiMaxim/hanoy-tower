/**
 * provides a way to store line data, etc.
 * new elements are stored at the start of the array
 * @param elements elements in the array
 * @param topOneIndex stores top index of the line
 * @param hash hash code of this line
 */
class Line(private val elements: ByteArray, private val topOneIndex: Int, val hash: String) {
    constructor(elements: ByteArray) : this(
        elements,
        elements.topOneIndex(),
        elements.hash()
    )

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
        return topOneIndex == disks
    }

    /**
     * @return copy of the line with a removed top element
     */
    fun removeTopElement(): Line {
        val hashValue = hash.replace(elements[topOneIndex - 1].toString(), "0")
        return Line(
            elements.copyOf().also { it[topOneIndex - 1] = 0 }, topOneIndex - 1, hashValue
        )
    }

    /**
     * @return copy of the line with the specific element added to the top
     */
    fun addElement(element: Byte): Line {
        val hashValue = hash.replaceFirst("0", element.toString())
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
     * @return topElement if exists, ($disks + 1) if line is full
     */
    fun topElement(): Byte {
        return if (empty()) {
            (disks + 1).toByte()
        } else {
            elements[topOneIndex - 1]
        }
    }

    /**
     * displays line in a human-readable form
     */
    fun display() {
        elements.forEach { if (it != 0.toByte()) print("$it ") else print("  ") }
    }
}

/**
 * @returns index of the first not null element or $disks
 */
fun ByteArray.topOneIndex(): Int {
    this.indexOfFirst { it == 0.toByte() }.let {
        return if (it == -1) {
            disks
        } else {
            it
        }
    }
}

/**
 * used for creating hash code on initialization
 * @return hash code
 */
fun ByteArray.hash(): String {
    var stringBuilder = ""
    this.forEach {
        stringBuilder += it
    }
    return stringBuilder
}