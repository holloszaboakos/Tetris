package boss.tetris.graphics.bitmap.tiny

import boss.tetris.model.Block
import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.ColorH
import boss.tetris.graphics.bitmap.toColor

@ExperimentalUnsignedTypes
class BTBlockRepresentation : BTRepresentation {
    lateinit var owner: Block
    override val representation: EasyWeightMap<ColorH>
        get() = EasyWeightMap(Array(9) { owner.color.toUInt().toColor() }, 3, 3.toUInt().toColor())
}