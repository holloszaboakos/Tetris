package boss.tetris.graphics

import boss.tetris.basics.Position
import boss.tetris.graphics.bitmap.Color

@ExperimentalUnsignedTypes
data class BitMap(var size: Position) {

    var pixels = Array(size.column * size.line) { Color.Named.TRANS() }

    constructor(_pixels: Array<Color>, columnSize: Int) : this(Position(columnSize, _pixels.size / columnSize)) {
        for (x in 0 until size.column)
            for (y in 0 until size.line)
                pixels[y * size.column + x] = _pixels[y * size.column + x]
    }

    operator fun get(xColumn: Int, yLine: Int): Color =
        pixels[(yLine % size.line) * size.column + (xColumn % size.column)]

    operator fun set(xColumn: Int, yLine: Int, value: Color) {
        pixels[(yLine % size.line) * size.column + (xColumn % size.column)] = value
    }

    operator fun get(index: Int): Color = pixels[index % (size.column * size.line)]
    operator fun set(index: Int, value: Color) {
        pixels[index % (size.column * size.line)] = value
    }

    fun drawOn(other: BitMap, startCorner: Position = Position(0, 0)): BitMap {
        for (x in 0 until other.size.column)
            for (y in 0 until other.size.line) {
                if (other[x, y] != Color.Named.TRANS())
                    this[x + startCorner.column, y + startCorner.line] += other[x, y]
            }
        return this
    }

    fun clear(c: Color = Color.Named.TRANS()): BitMap {
        for (idx in 0 until pixels.size)
            pixels[idx] = c.copy()
        return this
    }

}