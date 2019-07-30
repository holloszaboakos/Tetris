package boss.tetris.graphics.bitmap.flat

import android.graphics.Bitmap
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.base.StackRepresentation
import boss.tetris.graphics.bitmap.BRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.toBitmap
import boss.tetris.model.Stack

@ExperimentalUnsignedTypes
class BFStackRepresentation : StackRepresentation, BRepresentation {
    override var lastRep: Matrix<Bitmap>? = null
    override var lastState: Long = 0
    override lateinit var owner: Stack
    override val rep: Matrix<Bitmap>
        get() =
            if (lastRep != null && lastState == owner.stateCounter)
                lastRep as Matrix<Bitmap>
            else {
                lastState = owner.stateCounter
                lastRep = Matrix(
                    Array(owner.size.area) {
                        Matrix(Array(1) { owner.blockMap[it].color }, 1, Color.Named.TRANS()).toBitmap()
                    },
                    owner.size.column,
                    Matrix(BFGraphicFactory.TileSize, Color.Named.TRANS()).toBitmap()
                )
                lastRep as Matrix<Bitmap>
            }
}