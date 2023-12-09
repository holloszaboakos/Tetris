package boss.tetris.graphics.bitmap

import android.graphics.Bitmap
import boss.tetris.graphics.Matrix

@ExperimentalUnsignedTypes
interface BRepresentation {
	val representation: Matrix<Bitmap>
	var lastRepresentation: Matrix<Bitmap>?
}