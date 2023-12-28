import java.io.File
import kotlin.system.measureTimeMillis
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SampleTest {
    @Test
    fun test1() {
        val examplePos = Position(
            2, mutableListOf(
                Line(byteArrayOf(0, 0, 0, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 1, 2, 3))
            )
        )
        val solveResult = examplePos.startSolving(10)
        assert(solveResult.isNotEmpty() && solveResult.first().isNotEmpty())
        assert(solveResult.first().first().second == 7)
        val a0 = solveResult.first()[0]
        val b0 = (Position(
            2, mutableListOf(
                Line(byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
            )
        ) to 7)
        assertEquals(a0, b0)
        assert(solveResult.first().first().second == 7)
        val a1 = solveResult.first()[1]
        val b1 = (Position(
            2, mutableListOf(
                Line(byteArrayOf(0, 0, 3, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 2)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 1))
            )
        ) to -1)
        assertEquals(a1, b1)
        val a2 = solveResult.first()[2]
        val b2 = (Position(
            2, mutableListOf(
                Line(byteArrayOf(0, 0, 3, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 1, 2)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
            )
        ) to -1)
        assertEquals(a2, b2)
        val a3 = solveResult.first()[3]
        val b3 = (Position(
            2, mutableListOf(
                Line(byteArrayOf(0, 0, 0, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 1, 2)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 3))
            )
        ) to -1)
        assertEquals(a3, b3)
        val a4 = solveResult.first()[4]
        val b4 = (Position(
            2, mutableListOf(
                Line(byteArrayOf(0, 0, 1, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 2)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 3))
            )
        ) to -1)
        assertEquals(a4, b4)
        val a5 = solveResult.first()[5]
        val b5 = (Position(
            2, mutableListOf(
                Line(byteArrayOf(0, 0, 1, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 2, 3))
            )
        ) to -1)
        assertEquals(a5, b5)
        val a6 = solveResult.first()[6]
        val b6 = (Position(
            2, mutableListOf(
                Line(byteArrayOf(0, 0, 0, 4, 5, 6, 7, 8)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
                Line(byteArrayOf(0, 0, 0, 0, 0, 1, 2, 3))
            )
        ) to -1)
        assertEquals(a6, b6)
    }

    @Test
    fun test2() {
        for (i in 0..4) {
            val examplePos = Position(
                2, mutableListOf(
                    Line(byteArrayOf(0, 0, 0, 0, 0, 6, 7, 8)),
                    Line(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)),
                    Line(byteArrayOf(0, 0, 0, 1, 2, 3, 4, 5))
                )
            )
            val time = measureTimeMillis {
                examplePos.startSolving(16)
            }
            File("time").appendText("took $time ms to run $i\n")
        }
        File("time").appendText("attempt ended\n\n")
    }

}