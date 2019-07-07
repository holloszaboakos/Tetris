package boss.tetris.graphics.bitmap.tiny

import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.ColorH

@ExperimentalUnsignedTypes
interface BTRepresentation {
	val representation: EasyWeightMap<ColorH>
}