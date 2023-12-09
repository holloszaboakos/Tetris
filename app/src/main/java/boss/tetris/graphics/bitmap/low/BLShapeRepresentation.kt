package boss.tetris.graphics.bitmap.low

import android.graphics.Bitmap
import android.icu.util.Calendar
import boss.tetris.basics.Direction2D
import boss.tetris.basics.FreeWays
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.base.ShapeRepresentation
import boss.tetris.graphics.bitmap.BRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.bitmap.SkinStorage
import boss.tetris.graphics.toBitmap
import boss.tetris.model.Shapes

@ExperimentalUnsignedTypes
class BLShapeRepresentation : ShapeRepresentation, BRepresentation {
    override var lastRepresentation: Matrix<Bitmap>? = null
    override var lastState: Long = 0
    val reps = mutableMapOf<Direction2D, Matrix<Bitmap>>()
    override lateinit var owner: Shapes
    val tsize = BLGraphicFactory.TileSize
    override val representation: Matrix<Bitmap>
        get() {
            val time = Calendar.getInstance().timeInMillis
            if (lastRepresentation != null && lastState == owner.stateCounter)
                return lastRepresentation as Matrix<Bitmap>
            if (!reps.containsKey(owner.direction)) {
                for (id in 1..4) {
                    val di = Direction2D.values()[id]
                    val look = (owner.lookMap[di] as Matrix<FreeWays>)
                    val result = Matrix(
                        look.size,
                        Matrix(BLGraphicFactory.TileSize, Color.Named.TRANS()).toBitmap()
                    )
                    for (col in 0 until result.size.column)
                        for (lin in 0 until result.size.line)
                            if (look[col, lin] != look.emptyValue)
                                result[col, lin] =
                                    (SkinStorage.content[owner.color] as Map<FreeWays, Bitmap>)[look[col, lin]] as Bitmap
                    reps[di]=result
                }
            }
            lastState = owner.stateCounter
            lastRepresentation = reps[owner.direction]
            println("Time3!!! : " + (Calendar.getInstance().timeInMillis - time).toString())
            return reps[owner.direction] as Matrix<Bitmap>
        }

}