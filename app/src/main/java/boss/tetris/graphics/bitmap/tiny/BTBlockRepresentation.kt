package boss.tetris.graphics.bitmap.tiny

import boss.tetris.model.Block
import boss.tetris.graphics.EasyWeightMap
import boss.zubworkersinc.graphics.bitmap.tiny.BTRepresentation

class BTBlockRepresentation : BTRepresentation {
    lateinit var owner: Block
    override val representation: EasyWeightMap<Int>
        get() = EasyWeightMap(Array(9) { owner.color }, 3, 3)
}