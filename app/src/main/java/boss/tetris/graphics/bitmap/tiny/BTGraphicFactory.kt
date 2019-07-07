package boss.tetris.graphics.bitmap.tiny

import boss.tetris.basics.Position
import boss.tetris.view.MySurface

object BTGraphicFactory {

    lateinit var GraphicLoader: MySurface

    val TileSize get() = Position(3, 3)

    @ExperimentalUnsignedTypes
    val BlockRep get() = BTBlockRepresentation()

    @ExperimentalUnsignedTypes
    val ShapeRep get() = BTShapeRepresentation()

}