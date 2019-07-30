package boss.tetris.graphics.base

import boss.tetris.model.Shapes

@ExperimentalUnsignedTypes
interface ShapeRepresentation  {
    var owner: Shapes
    var lastState: Long
}