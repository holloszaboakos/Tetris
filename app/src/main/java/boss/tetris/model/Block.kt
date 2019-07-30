package boss.tetris.model

import boss.tetris.basics.Dir2D
import boss.tetris.basics.FreeWays
import boss.tetris.graphics.base.StackRepresentation
import boss.tetris.graphics.bitmap.Color

@ExperimentalUnsignedTypes
class Block {
    var stateCounter: Long = 0
    var color = Color.Named.WHITE()
        set(value) {
            field = value;stateCounter++
        }
    var isWay = FreeWays.AL
    operator fun get(d: Dir2D) = isWay[d]
    operator fun set(d: Dir2D, b: Boolean) {
        isWay = isWay[d, b];stateCounter++
    }
}