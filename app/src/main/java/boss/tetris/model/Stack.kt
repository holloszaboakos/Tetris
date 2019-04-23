package boss.tetris.model

import android.content.Context
import android.content.Intent
import android.graphics.Color
import boss.tetris.GameSurface
import boss.tetris.basics.Direction
import boss.tetris.basics.Position
import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.tiny.BTGraphicFactory

object Stack : TickerClock.tickable {
    var point=0
    val size = Position(10, 20)
    val blockMap = Array(size.line) { Array(size.column) { Block(BTGraphicFactory.BlockRep) } }
    var shapePosition = Position(size.column/2-1, 0)
    var shape = Shapes.values()[(0..6).random()]
    lateinit var actualGS:GameSurface

    init {
        TickerClock.tickables.add(this)
    }

    fun reset() {
        point=0
        for (line in blockMap)
            for (block in line)
                block.color = Color.BLACK
        newShape()
    }

    operator fun get(coordinate: Position) = blockMap[coordinate.column][coordinate.line]
    override fun tick() {
        for (lin in 1 until size.line - 0) {
            var full = true
            for (col in 0 until size.column) {
                if (blockMap[size.line - lin][col].color == Color.BLACK)
                    full = false
            }
            if (full) {
                for (col in 0 until size.column) {
                    blockMap[size.line - lin][col].color = Color.BLACK
                }
                point+=100
                actualGS.setPoint(point)
            }
        }

        var empty: Boolean
        for (lin in 1 until size.line) {
            empty = true
            for (col in 0 until size.column)
                if (blockMap[size.line - lin][col].color != Color.BLACK &&
                    blockMap[size.line - lin - 1][col].color != Color.BLACK
                ) {
                    empty = false
                }
            if (empty) {
                for (col in 0 until size.column)
                    if (blockMap[size.line - lin - 1][col].color != Color.BLACK) {
                        val tmp = blockMap[size.line - lin][col]
                        blockMap[size.line - lin][col] = blockMap[size.line - lin - 1][col]
                        blockMap[size.line - lin - 1][col] = tmp
                    }
            }
        }

        if (isHit(shape.Look, shapePosition + Direction.DOWN.vector)) {
            println("DOWN")
            point+=10
            actualGS.setPoint(point)
            for (col in 0 until shape.Look.columnSize)
                for (lin in 0 until shape.Look.lineSize)
                    if (shape.Look[col, lin])
                        blockMap[shapePosition.line + lin][shapePosition.column + col].color = shape.color
            newShape()
        } else
            shapePosition += Direction.DOWN.vector
    }

    fun newShape() {
        val newPosition = Position(size.column/2-1, 0)
        val newShape = Shapes.values()[(0..6).random()]
        if (TickerClock.time != 100.toLong())
            TickerClock.time -= 5
        if (isHit(newShape.Look, newPosition)) {
            actualGS.gameEnded()
        }
        else{
            shape=newShape
            shapePosition=newPosition

        }
    }

    fun isHit(look: EasyWeightMap<Boolean>, pos: Position): Boolean {
        var result = false
        for (col in 0 until look.columnSize)
            for (lin in 0 until look.lineSize)
                if (pos.column + col >= size.column ||
                    pos.column < 0 ||
                    pos.line + lin >= size.line ||
                    pos.line < 0 ||
                    blockMap[pos.line + lin][pos.column + col].color != Color.BLACK && look[col, lin]
                )
                    result = true
        return result
    }

}

