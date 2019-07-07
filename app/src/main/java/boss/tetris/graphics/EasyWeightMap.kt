package boss.tetris.graphics

import boss.tetris.basics.Position

class EasyWeightMap< T : Any >(var columnSize: Int, var lineSize: Int, var empty:T) {
    @Suppress("UNCHECKED_CAST")
    var pixels: Array<T> = Array<Any>(columnSize*lineSize){empty} as Array<T>
    operator fun get(xColumn: Int, yLine: Int): T =
        pixels[(yLine % lineSize) * columnSize + (xColumn % columnSize)]

    operator fun set(xColumn: Int, yLine: Int, value: T) {
        pixels[(yLine % lineSize) * columnSize + (xColumn % columnSize)] = value
    }

    operator fun get(index: Int): T = pixels[index % (columnSize * lineSize)]
    operator fun set(index: Int, value: T) {
        pixels[index % (columnSize * lineSize)] = value
    }

    fun drawOn(other: EasyWeightMap<T>, startCorner: Position) {
        for (x in 0 until other.columnSize)
            for (y in 0 until other.lineSize) {
                if (other[x, y] != other.empty)
                    this[x + startCorner.column, y + startCorner.line] = other[x, y]
                //this[x + startCorner.column, y + startCorner.line].plusColor(other[x, y])
            }
    }

    constructor(_pixels: Array<T>, columnSize: Int,empty:T) : this(columnSize, _pixels.size / columnSize,empty) {
        for (x in 0 until columnSize)
            for (y in 0 until lineSize)
                pixels[y * columnSize + x] = _pixels[y * columnSize + x]
    }
}