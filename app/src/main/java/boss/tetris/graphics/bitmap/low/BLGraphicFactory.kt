package boss.tetris.graphics.bitmap.flat

import boss.tetris.basics.Position
import boss.tetris.graphics.base.GraphicFactory
import boss.tetris.view.TSurfaceView

@ExperimentalUnsignedTypes
object BFGraphicFactory:GraphicFactory {

    override lateinit var graphicLoader: TSurfaceView

    override val TileSize get() = Position(3, 3)

    @ExperimentalUnsignedTypes
    override val BlockRep get() = BFBlockRepresentation()

    @ExperimentalUnsignedTypes
    override val ShapeRep get() = BFShapeRepresentation()

}