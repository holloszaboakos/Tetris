package boss.tetris.graphics.bitmap.low

import boss.tetris.basics.Vec2D
import boss.tetris.graphics.base.GraphicFactory
import boss.tetris.view.StackSView

@ExperimentalUnsignedTypes
object BLGraphicFactory: GraphicFactory {

    override lateinit var graphicLoader: StackSView

    override val TileSize get() = Vec2D(8, 8)

    @ExperimentalUnsignedTypes
    override val StackRep get() = BLStackRepresentation()

    @ExperimentalUnsignedTypes
    override val ShapeRep get() = BLShapeRepresentation()

}