package boss.tetris.model

import android.graphics.Color
import boss.tetris.graphics.bitmap.tiny.BTBlockRepresentation

@ExperimentalUnsignedTypes
class Block(val representation: BTBlockRepresentation){
    var id=-1
    var color= Color.BLACK
    init{
        representation.owner=this
    }
}