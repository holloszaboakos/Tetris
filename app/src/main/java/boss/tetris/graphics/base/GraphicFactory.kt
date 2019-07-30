package boss.tetris.graphics.bitmap.tiny

import boss.tetris.basics.Position
import boss.tetris.view.TSurfaceView

@ExperimentalUnsignedTypes
object BTGraphicFactory {

    lateinit var graphicLoader: TSurfaceView

    val TileSize get() = Position(3, 3)

    @ExperimentalUnsignedTypes
    val BlockRep get() = BTBlockRepresentation()

    @ExperimentalUnsignedTypes
    val ShapeRep get() = BTShapeRepresentation()

}