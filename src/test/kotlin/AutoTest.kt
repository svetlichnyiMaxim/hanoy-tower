import kotlin.test.Test

class AutoTest {
    @Test
    fun test() {
        val examplePos = Position(
            2,
            arrayOf(
                Line(byteArrayOf(8, 7, 6, 5)),
                Line(byteArrayOf()),
                Line(byteArrayOf(4, 3, 2, 1))
            ), 8
        )
        val solveResult = solve(examplePos)
        assert(solveResult.first.size == 16)
        assert(solveResult.second < 300L)
        occurredPositions.clear()
    }

    @Test
    fun test1() {
        val examplePos = Position(
            2,
            arrayOf(
                Line(byteArrayOf(8, 7, 6)),
                Line(byteArrayOf()),
                Line(byteArrayOf(5, 4, 3, 2, 1))
            ), 8
        )
        val solveResult = solve(examplePos)
        assert(solveResult.first.size == 32)
        assert(solveResult.second < 300L)
        occurredPositions.clear()
    }

    @Test
    fun test2() {
        val examplePos = Position(
            2,
            arrayOf(
                Line(byteArrayOf(8, 7)),
                Line(byteArrayOf()),
                Line(byteArrayOf(6, 5, 4, 3, 2, 1)),
            ), 8
        )
        val solveResult = solve(examplePos)
        assert(solveResult.first.size == 64)
        assert(solveResult.second < 1000L)
        occurredPositions.clear()
    }

    @Test
    fun test3() {
        val examplePos = Position(
            2,
            arrayOf(
                Line(byteArrayOf(8)),
                Line(byteArrayOf()),
                Line(byteArrayOf(7, 6, 5, 4, 3, 2, 1))
            ),
            8
        )
        val solveResult = solve(examplePos)
        assert(solveResult.first.size == 128)
        assert(solveResult.second < 2500L)
        occurredPositions.clear()
    }

    @Test
    fun test4() {
        val examplePos = Position(
            2,
            arrayOf(
                Line(byteArrayOf()),
                Line(byteArrayOf()),
                Line(byteArrayOf(8, 7, 6, 5, 4, 3, 2, 1))
            ),
            8
        )
        val solveResult = solve(examplePos)
        assert(solveResult.first.size == 256)
        assert(solveResult.second < 5000L)
        occurredPositions.clear()
    }
}
