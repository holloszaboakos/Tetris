package boss.tetris.model

import boss.tetris.basics.Direction2D
import boss.tetris.basics.FreeWays
import boss.tetris.graphics.bitmap.Color

@ExperimentalUnsignedTypes
class Block {
    var stateCounter: Long = 0
    var color = Color.Named.WHITE()
        set(value) {
            field = value;stateCounter++
        }
    var isWay = FreeWays.FULL_OPEN
    operator fun get(d: Direction2D) = isWay[d]
    operator fun set(d: Direction2D, b: Boolean) {
        isWay = isWay[d, b];stateCounter++
    }
}