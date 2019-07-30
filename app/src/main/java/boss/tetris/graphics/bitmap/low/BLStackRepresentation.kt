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
    override var lastRep: Matrix<Bitmap>? = null
    override var lastState: Long = 0
    override lateinit var owner: Stack


    override val rep: Matrix<Bitmap>
        get() {
            if (lastRep != null && lastState == owner.stateCounter)
                lastRep
            val result = Matrix(
                Array(owner.size.area) {
                    if (owner.blockMap[it].isWay == FreeWays.AL)
                        Matrix(BLGraphicFactory.TileSize, Color.Named.TRANS()).toBitmap()
                    else
                        (SkinStorage.content[owner.blockMap[it].color] as Map<FreeWays, Bitmap>)[owner.blockMap[it].isWay] as Bitmap
                },
                owner.size.column,
                Matrix(BLGraphicFactory.TileSize, Color.Named.TRANS()).toBitmap()
            )
            lastState = owner.stateCounter
            lastRep = result
            return lastRep as Matrix<Bitmap>
        }
}