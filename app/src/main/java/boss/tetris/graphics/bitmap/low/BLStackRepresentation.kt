package boss.tetris.graphics.bitmap.low

import android.graphics.Bitmap
import boss.tetris.basics.FreeWays
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.base.BlockRepresentation
import boss.tetris.graphics.bitmap.BRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.bitmap.SkinStorage
import boss.tetris.graphics.toBitMap
import boss.tetris.model.Block

@ExperimentalUnsignedTypes
class BLBlockRepresentation : BlockRepresentation, BRepresentation {
    override var lastRep: Matrix<Bitmap>? =null
    override var lastState: Long = 0
    override lateinit var owner: Block


    override val rep: Matrix<Bitmap>
        get() {
            if(lastRep!=null&&lastState==owner.stateCounter)
                lastRep
            val result = (SkinStorage.content[owner.color] as Map<FreeWays,Bitmap>)[owner.isWay] as Bitmap
            lastState=owner.stateCounter
            lastRep = Matrix(arrayOf(result),1, Matrix(BLGraphicFactory.TileSize,Color.Named.TRANS()).toBitMap())
            return lastRep as Matrix<Bitmap>
        }
}