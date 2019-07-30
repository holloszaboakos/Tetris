package boss.tetris.graphics.base
import boss.tetris.model.Stack

@ExperimentalUnsignedTypes
interface StackRepresentation {
    var owner: Stack
    var lastState: Long
}