package boss.tetris.view

import boss.tetris.basics.Vec2D
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.bitmap.low.BLGraphicFactory
import boss.tetris.model.Stack

@ExperimentalUnsignedTypes
object ViewDataContainer {
    val factory = BLGraphicFactory
    val tileSize = factory.TileSize
    val levelSize = Stack.size
    val size = Vec2D(tileSize.column * levelSize.column, tileSize.line * levelSize.line)
}