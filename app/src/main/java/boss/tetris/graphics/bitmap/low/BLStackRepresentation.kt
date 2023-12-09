package boss.tetris.graphics.bitmap.low

import android.graphics.Bitmap
import boss.tetris.basics.FreeWays
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.base.StackRepresentation
import boss.tetris.graphics.bitmap.BRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.bitmap.SkinStorage
import boss.tetris.graphics.toBitmap
import boss.tetris.model.Stack

@ExperimentalUnsignedTypes
class BLStackRepresentation : StackRepresentation, BRepresentation {
    override var lastRepresentation: Matrix<Bitmap>? = null
    override var lastState: Long = 0
    override lateinit var owner: Stack


    override val representation: Matrix<Bitmap>
        get() {
            if (lastRepresentation != null && lastState == owner.stateCounter)
                lastRepresentation
            val result = Matrix(
                Array(owner.size.area) {
                    if (owner.blocks[it].isWay == FreeWays.FULL_OPEN)
                        Matrix(BLGraphicFactory.TileSize, Color.Named.TRANS()).toBitmap()
                    else
                        (SkinStorage.content[owner.blocks[it].color] as Map<FreeWays, Bitmap>)[owner.blocks[it].isWay] as Bitmap
                },
                owner.size.column,
                Matrix(BLGraphicFactory.TileSize, Color.Named.TRANS()).toBitmap()
            )
            lastState = owner.stateCounter
            lastRepresentation = result
            return lastRepresentation as Matrix<Bitmap>
        }
}