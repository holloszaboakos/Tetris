package boss.tetris.model

import boss.tetris.GameSurface
import boss.tetris.basics.Direction2D
import boss.tetris.basics.FreeWays
import boss.tetris.basics.Vec2D
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.bitmap.Color
import boss.tetris.view.ViewDataContainer

@ExperimentalUnsignedTypes
object Stack : TickerClock.tickable {
    var stateCounter: Long = 0
    var point = 0
    val size = Vec2D(10, 20)
    var speeding:Int=5
    val blocks = Matrix(
        Array(size.area) { Block() },
        size.column,
        Block()
    )
    var shapePosition = Vec2D(size.column / 2 - 1, 0)
        set(value) {
            field = value;stateCounter++
        }
    var shape = Shapes.values()[(0 until Shapes.values().size).random()]
    lateinit var actualGS: GameSurface
    val rep = ViewDataContainer.factory.StackRep

    init {
        TickerClock.tickables.add(this)
        rep.owner = this
    }

    fun reset() {
        point = 0
        for (col in 0 until blocks.size.column)
            for (lin in 0 until blocks.size.line) {
                blocks[col, lin].color = Color.Named.WHITE()
                blocks[col, lin].isWay = FreeWays.FULL_OPEN
            }
        newShape()
        stateCounter++
    }

    operator fun get(coordinate: Vec2D) = blocks[coordinate]

    override fun tick() {
        for (lin in 1 until size.line - 0) {
            var full = true
            for (col in 0 until size.column) {
                if (blocks[col, size.line - lin].color == Color.Named.WHITE())
                    full = false
            }
            if (full) {
                for (col in 0 until size.column) {
                    blocks[col, size.line - lin].color = Color.Named.WHITE()
                    blocks[col, size.line - lin].isWay = FreeWays.FULL_OPEN
                }
                point += 100
                actualGS.setPoint(point)
            }
        }

        var empty: Boolean
        for (lin in 1 until size.line) {
            empty = true
            for (col in 0 until size.column)
                if (blocks[col, size.line - lin].color != Color.Named.WHITE() &&
                    blocks[col, size.line - lin - 1].color != Color.Named.WHITE()
                ) {
                    empty = false
                }
            if (empty) {
                for (col in 0 until size.column)
                    if (blocks[col, size.line - lin - 1].color != Color.Named.WHITE()) {
                        val tmp = blocks[col, size.line - lin]
                        blocks[col, size.line - lin] = blocks[col, size.line - lin - 1]
                        blocks[col, size.line - lin - 1] = tmp
                    }
            }
        }

        if (isHit(shape.Look, shapePosition + Direction2D.DOWN.vector)) {
            point += 10
            actualGS.setPoint(point)
            for (col in 0 until shape.Look.size.column)
                for (lin in 0 until shape.Look.size.line)
                    if (shape.Look[col, lin] != shape.Look.emptyValue) {
                        blocks[shapePosition.column + col, shapePosition.line + lin].color = shape.color
                        blocks[shapePosition.column + col, shapePosition.line + lin].isWay = shape.Look[col, lin]
                    }
            newShape()
        } else
            shapePosition += Direction2D.DOWN.vector
        stateCounter++
    }

    fun newShape() {
        val newPosition = Vec2D(size.column / 2 - 1, -shape.Look.size.line)
        val newShape = Shapes.values()[(0 until Shapes.values().size).random()]
        newShape.direction = Direction2D.UP
        if (TickerClock.time != 100.toLong())
            TickerClock.time -= speeding
        if (isHit(newShape.Look, newPosition)) {
            actualGS.gameEnded()
        } else {
            shape = newShape
            shapePosition = newPosition

        }
        stateCounter++
    }

    fun isHit(look: Matrix<FreeWays>, pos: Vec2D): Boolean {
        var result = false
        for (col in 0 until look.size.column)
            for (lin in 0 until look.size.line)
                if (pos.column + col >= size.column ||
                    pos.column < 0 ||
                    pos.line + lin >= size.line ||
                    (pos.line >= 0 &&
                            blocks[pos.column + col, pos.line + lin].color != Color.Named.WHITE()
                            && look[col, lin] != look.emptyValue)

                )
                    result = true
        stateCounter++
        return result
    }

}

