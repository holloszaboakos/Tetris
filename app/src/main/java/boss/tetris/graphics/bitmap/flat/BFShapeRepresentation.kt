package boss.tetris.graphics.bitmap.flat

import android.graphics.Bitmap
import boss.tetris.basics.FreeWays
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.base.ShapeRepresentation
import boss.tetris.graphics.bitmap.BRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.toBitmap
import boss.tetris.model.Shapes

@ExperimentalUnsignedTypes
class BFShapeRepresentation : ShapeRepresentation, BRepresentation {
    override var lastRep: Matrix<Bitmap>? = null
    override var lastState: Long = 0
    override lateinit var owner: Shapes
    override val rep: Matrix<Bitmap>
        get() {
            if (lastRep != null && lastState == owner.stateCounter)
                lastRep
            val look = owner.Look
            val result = Matrix(
                arrayOf(
                    Matrix(
                        Array(look.size.area)
                        {
                            val col = it%look.size.column
                            val lin = it/look.size.column
                            if (look[col, lin] != FreeWays.IS)
                                owner.color
                            else
                                Color.Named.TRANS()},
                        look.size.column * BFGraphicFactory.TileSize.column,
                        Color.Named.TRANS()
                    ).toBitmap()
                ), 1, Matrix(BFGraphicFactory.TileSize, Color.Named.TRANS()).toBitmap()
            )
            lastState = owner.stateCounter
            lastRep = result
            return result
        }

}