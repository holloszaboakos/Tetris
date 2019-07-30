package boss.tetris.graphics.bitmap.flat

import android.graphics.Bitmap
import boss.tetris.basics.Vec2D
import boss.tetris.model.Block
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.base.BlockRepresentation
import boss.tetris.graphics.bitmap.BRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.toBitMap

@ExperimentalUnsignedTypes
class BFBlockRepresentation : BlockRepresentation, BRepresentation {
    override var lastRep: Matrix<Bitmap>? = null
    override var lastState: Long = 0
    override lateinit var owner: Block
    override val rep: Matrix<Bitmap>
        get() =
            if (lastRep != null && lastState == owner.stateCounter)
                lastRep as Matrix<Bitmap>
            else {
                lastState = owner.stateCounter
                lastRep = Matrix(
                    arrayOf(Matrix(Array(1) { owner.color }, 1, Color.Named.TRANS()).toBitMap()), 1,
                    Matrix(BFGraphicFactory.TileSize, Color.Named.TRANS()).toBitMap()
                )
                lastRep as Matrix<Bitmap>
            }
}