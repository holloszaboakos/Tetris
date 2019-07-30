package boss.tetris.model

import boss.tetris.GameSurface
import boss.tetris.basics.Dir2D
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
    val blockMap = Matrix(
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
        for (col in 0 until blockMap.size.column)
            for (lin in 0 until blockMap.size.line) {
                blockMap[col, lin].color = Color.Named.WHITE()
                blockMap[col, lin].isWay = FreeWays.AL
            }
        newShape()
        stateCounter++
    }

    operator fun get(coordinate: Vec2D) = blockMap[coordinate]

    override fun tick() {
        for (lin in 1 until size.line - 0) {
            var full = true
            for (col in 0 until size.column) {
                if (blockMap[col, size.line - lin].color == Color.Named.WHITE())
                    full = false
            }
            if (full) {
                for (col in 0 until size.column) {
                    blockMap[col, size.line - lin].color = Color.Named.WHITE()
                    blockMap[col, size.line - lin].isWay = FreeWays.AL
                }
                point += 100
                actualGS.setPoint(point)
            }
        }

        var empty: Boolean
        for (lin in 1 until size.line) {
            empty = true
            for (col in 0 until size.column)
                if (blockMap[col, size.line - lin].color != Color.Named.WHITE() &&
                    blockMap[col, size.line - lin - 1].color != Color.Named.WHITE()
                ) {
                    empty = false
                }
            if (empty) {
                for (col in 0 until size.column)
                    if (blockMap[col, size.line - lin - 1].color != Color.Named.WHITE()) {
                        val tmp = blockMap[col, size.line - lin]
                        blockMap[col, size.line - lin] = blockMap[col, size.line - lin - 1]
                        blockMap[col, size.line - lin - 1] = tmp
                    }
            }
        }

        if (isHit(shape.Look, shapePosition + Dir2D.DOWN.vector)) {
            point += 10
            actualGS.setPoint(point)
            for (col in 0 until shape.Look.size.column)
                for (lin in 0 until shape.Look.size.line)
                    if (shape.Look[col, lin] != shape.Look.emptyValue) {
                        blockMap[shapePosition.column + col, shapePosition.line + lin].color = shape.color
                        blockMap[shapePosition.column + col, shapePosition.line + lin].isWay = shape.Look[col, lin]
                    }
            newShape()
        } else
            shapePosition += Dir2D.DOWN.vector
        stateCounter++
    }

    fun newShape() {
        val newPosition = Vec2D(size.column / 2 - 1, -shape.Look.size.line)
        val newShape = Shapes.values()[(0 until Shapes.values().size).random()]
        newShape.direction = Dir2D.UP
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
                            blockMap[pos.column + col, pos.line + lin].color != Color.Named.WHITE()
                            && look[col, lin] != look.emptyValue)

                )
                    result = true
        stateCounter++
        return result
    }

}

