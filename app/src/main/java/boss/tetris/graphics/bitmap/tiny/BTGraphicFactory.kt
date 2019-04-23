package boss.tetris.graphics.bitmap.tiny

import boss.tetris.basics.Position
import boss.tetris.view.MySurface

object BTGraphicFactory {

    lateinit var GraphicLoader: MySurface

    val TileSize get() = Position(3, 3)

    val BlockRep get() = BTBlockRepresentation()

    val ShapeRep get() = BTShapeRepresentation()

}