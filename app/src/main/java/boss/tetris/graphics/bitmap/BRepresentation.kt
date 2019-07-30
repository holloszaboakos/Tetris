package boss.tetris.graphics.bitmap

import android.graphics.Bitmap
import boss.tetris.graphics.Matrix

@ExperimentalUnsignedTypes
interface BRepresentation {
	val rep: Matrix<Bitmap>
	var lastRep: Matrix<Bitmap>?
}