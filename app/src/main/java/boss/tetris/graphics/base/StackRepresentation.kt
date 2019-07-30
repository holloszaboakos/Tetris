package boss.tetris.graphics.base

import boss.tetris.model.Block

@ExperimentalUnsignedTypes
interface BlockRepresentation {
    var owner: Block
    var lastState: Long
}