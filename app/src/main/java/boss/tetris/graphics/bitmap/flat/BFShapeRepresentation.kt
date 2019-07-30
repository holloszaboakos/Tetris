package boss.tetris.graphics.bitmap.tiny

import android.graphics.Color
import android.util.AndroidException
import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.ColorH
import boss.tetris.graphics.bitmap.toColor
import boss.tetris.model.Shapes

@ExperimentalUnsignedTypes
class BTShapeRepresentation : BTRepresentation {
    lateinit var owner: Shapes
    override val representation: EasyWeightMap<ColorH>
        get() {
            val result = EasyWeightMap(
                owner.Look.columnSize * BTGraphicFactory.TileSize.column,
                owner.Look.lineSize * BTGraphicFactory.TileSize.line,
                Color.BLACK.toUInt().toColor()
            )
            for (col in 0 until owner.Look.columnSize)
                for (lin in 0 until owner.Look.lineSize)
                    if (owner.Look[col, lin])
                        for (i in 0 until BTGraphicFactory.TileSize.column)
                            for (j in 0 until BTGraphicFactory.TileSize.line)
                                result[col * 3 + i, lin * 3 + j] = owner.color.toUInt().toColor()
                    else
                        for (i in 0 until BTGraphicFactory.TileSize.column)
                            for (j in 0 until BTGraphicFactory.TileSize.line)
                                result[col * 3 + i, lin * 3 + j] =Color.BLACK.toUInt().toColor()
            return result
        }

}