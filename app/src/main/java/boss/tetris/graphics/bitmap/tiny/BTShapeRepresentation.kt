package boss.tetris.graphics.bitmap.tiny

import android.graphics.Color
import boss.tetris.graphics.EasyWeightMap
import boss.tetris.model.Shapes
import boss.zubworkersinc.graphics.bitmap.tiny.BTRepresentation

class BTShapeRepresentation : BTRepresentation {
    lateinit var owner: Shapes
    override val representation: EasyWeightMap<Int>
        get() {
            val result = EasyWeightMap(
                owner.Look.columnSize * BTGraphicFactory.TileSize.column,
                owner.Look.lineSize * BTGraphicFactory.TileSize.line,
                Color.BLACK
            )
            for (col in 0 until owner.Look.columnSize)
                for (lin in 0 until owner.Look.lineSize)
                    if (owner.Look[col, lin])
                        for (i in 0 until BTGraphicFactory.TileSize.column)
                            for (j in 0 until BTGraphicFactory.TileSize.line)
                                result[col * 3 + i, lin * 3 + j] = owner.color
                    else
                        for (i in 0 until BTGraphicFactory.TileSize.column)
                            for (j in 0 until BTGraphicFactory.TileSize.line)
                                result[col * 3 + i, lin * 3 + j] =Color.BLACK
            return result
        }

}