package boss.tetris.view

import boss.tetris.graphics.Matrix
import boss.tetris.graphics.bitmap.Color

@ExperimentalUnsignedTypes
object MatrixBuffer {
    private val buffer = Array(2) {
        Matrix(ViewDataContainer.size, Color.Named.TRANS())
    }
    private var writerHead = 0
    private var readerHead = 0

    fun reset() {
        writerHead = 0
        readerHead = 0
    }

    val isEmpty:Boolean get() = writerHead - readerHead <= 0
    val isFull:Boolean get() = writerHead - readerHead >= buffer.size

    fun pop(): Matrix<Color> {
        synchronized(buffer[(readerHead+1)% buffer.size]) {
            if (isEmpty) throw Exception("no data to read")
            readerHead++
            return buffer[readerHead % buffer.size]
        }
    }

    fun put(ewm: Matrix<Color>) {
        synchronized(buffer[(writerHead+1)% buffer.size]) {
            if (isFull) throw Exception("no space to write to")
            writerHead++
            buffer[writerHead % buffer.size]=ewm
        }
    }
}