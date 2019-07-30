package boss.tetris.graphics

import android.graphics.Bitmap
import boss.tetris.basics.Dir1D
import boss.tetris.basics.Vec2D
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.bitmap.toUInt

data class Matrix<T : Any>(var size: Vec2D, val emptyValue: T) {

    @Suppress("UNCHECKED_CAST")
    var pixels: Array<T> = Array<Any>(size.column * size.line) { emptyValue } as Array<T>

    constructor(_pixels: Array<T>, columnSize: Int, emptyValue: T) : this(
        Vec2D(
            columnSize,
            _pixels.size / columnSize
        ), emptyValue
    ) {
        for (x in 0 until size.column)
            for (y in 0 until size.line)
                pixels[y * size.column + x] = _pixels[y * size.column + x]
    }

    operator fun get(p: Vec2D): T =
        pixels[(p.line % size.line) * size.column + (p.column % size.column)]

    operator fun set(p: Vec2D, value: T) {
        pixels[(p.line % size.line) * size.column + (p.column % size.column)] = value
    }

    operator fun get(xColumn: Int, yLine: Int): T =
        pixels[(yLine % size.line) * size.column + (xColumn % size.column)]

    operator fun set(xColumn: Int, yLine: Int, value: T) {
        pixels[(yLine % size.line) * size.column + (xColumn % size.column)] = value
    }

    operator fun get(index: Int): T = pixels[index % (size.column * size.line)]
    operator fun set(index: Int, value: T) {
        pixels[index % (size.column * size.line)] = value
    }

    operator fun get(d: Dir1D): Matrix<T> {
        val result = Matrix(Vec2D(size.line, size.column), emptyValue)
        when (d) {
            Dir1D.RIGHT ->
                for (c in 0 until size.column)
                    for (l in 0 until size.line)
                        result[size.line - 1 - l, c] = this[c, l]
            Dir1D.LEFT ->
                for (c in 0 until size.column)
                    for (l in 0 until size.line)
                        result[l, size.column - 1 - c] = this[c, l]
            Dir1D.NONE ->
                for (c in 0 until size.column)
                    for (l in 0 until size.line)
                        result[l, c] = this[c, l]
        }
        return result
    }

}


@ExperimentalUnsignedTypes
fun Matrix<Color>.drawOn(other: Matrix<Color>, startCorner: Vec2D = Vec2D(0, 0)): Matrix<Color> {
    for (x in 0 until other.size.column)
        for (y in 0 until other.size.line) {
            if (other[x, y] != Color.Named.TRANS())
                this[x + startCorner.column, y + startCorner.line] += other[x, y]
        }
    return this
}

@ExperimentalUnsignedTypes
fun Matrix<Color>.set(other: Matrix<Color>, startCorner: Vec2D = Vec2D(0, 0)) {
    for (x in 0 until other.size.column)
        for (y in 0 until other.size.line) {
            if (other[x, y] != Color.Named.TRANS())
                this[x + startCorner.column, y + startCorner.line] = other[x, y]
        }
}

@ExperimentalUnsignedTypes
fun Matrix<Color>.clear(c: Color = Color.Named.TRANS()): Matrix<Color> {
    for (idx in 0 until pixels.size)
        pixels[idx] = c.copy()
    return this
}

@ExperimentalUnsignedTypes
fun Matrix<Color>.toBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(size.column, size.line, Bitmap.Config.ARGB_8888)
    for (lin in 0 until size.line) {
        for (col in 0 until size.column) {
            bmp.setPixel(col, lin, this[col, lin].toUInt().toInt())
        }
    }
    return bmp
}