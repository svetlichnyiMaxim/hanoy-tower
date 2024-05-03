import java.util.*

/**
 * provides a way to store line data, etc.
 * new elements are stored at the start of the array
 * @param elements elements in the array
 * @param hash hash code of this line
 * @sample
 * Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
 * Line(byteArrayOf(8, 7, 6, 5, 4, 3, 2, 1))
 */
class Line(private val elements: Stack<Byte>, var hash: String) {
    constructor(elements: ByteArray = byteArrayOf()) : this(
        Stack<Byte>().apply {
            elements.forEach {
                this.push(it)
            }
        },
        elements.hash()
    )

    /**
     * checks if line is full
     * @return if the line is full
     */
    fun size(): Int {
        return elements.size
    }

    /**
     * @return copy of the line with a removed top element
     */
    fun pop() {
        hash = hash.dropLast(elements.peek().toString().length)
        elements.pop()
    }

    /**
     * @return copy of the line with the specific element added to the top
     */
    fun addElement(element: Byte) {
        hash += element.toString()
        elements.push(element)
    }

    /**
     * used to determine if the line is empty
     */
    fun empty(): Boolean {
        return elements.empty()
    }

    /**
     * @return topElement if exists, ($disks + 1) if line is full
     */
    fun peek(): Byte {
        return elements.peek()
    }

    /**
     * displays line in a human-readable form
     */
    fun display() {
        elements.forEach {
            if (it != 0.toByte())
                print("$it ")
            else
                print("  ")
        }
    }

    fun copy(): Line {
        return Line(elements.copy(), hash)
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

/**
 * creates copy of the byte stack
 */
fun Stack<Byte>.copy(): Stack<Byte> {
    val stack = Stack<Byte>()
    this.forEach {
        stack.push(it)
    }
    return stack
}
