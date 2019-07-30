package boss.tetris.graphics.base

import boss.tetris.basics.Vec2D
import boss.tetris.view.StackSView

@ExperimentalUnsignedTypes
interface GraphicFactory {

    var graphicLoader: StackSView

    val TileSize: Vec2D

    val StackRep: StackRepresentation

    val ShapeRep: ShapeRepresentation

}