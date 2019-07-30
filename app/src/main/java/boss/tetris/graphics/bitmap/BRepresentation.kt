package boss.tetris.graphics.bitmap.flat

import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.ColorH

@ExperimentalUnsignedTypes
interface BFRepresentation {
	val representation: EasyWeightMap<ColorH>
}