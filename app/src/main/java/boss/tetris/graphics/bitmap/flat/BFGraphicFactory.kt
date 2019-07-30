package boss.tetris.graphics.bitmap.flat

import boss.tetris.basics.Vec2D
import boss.tetris.graphics.base.GraphicFactory
import boss.tetris.view.StackSView

@ExperimentalUnsignedTypes
object BFGraphicFactory: GraphicFactory {

    override lateinit var graphicLoader: StackSView

    override val TileSize get() = Vec2D(1,1)

    @ExperimentalUnsignedTypes
    override val StackRep get() = BFStackRepresentation()

    @ExperimentalUnsignedTypes
    override val ShapeRep get() = BFShapeRepresentation()

}