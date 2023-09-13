import kotlin.test.Test
import kotlin.test.assertEquals

internal class SampleTest {

    private val testSample: Main = Main()

    @Test
    fun testPossibleMoves() {
        val emptyLine = Main.Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0))
        val emptyLine1 = Main.Line(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0))
        val def = Main.Position(
            mutableListOf(
                Main.Line(mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)),
                emptyLine.copy(),
                emptyLine1.copy()
            )
        )
        def.display()
        assertEquals(mutableListOf(Pair(0, 1), Pair(0, 2)), def.possibleMove())
    }
}