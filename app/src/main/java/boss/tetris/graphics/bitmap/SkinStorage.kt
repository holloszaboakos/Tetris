package boss.tetris.graphics.bitmap

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import boss.tetris.R
import boss.tetris.basics.Dir2D
import boss.tetris.basics.FreeWays
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.toBitmap
import boss.tetris.view.ViewDataContainer

@ExperimentalUnsignedTypes
object SkinStorage {
    val content = mutableMapOf<Color, MutableMap<FreeWays, Bitmap>>()
    fun set(c: Context) {
        if (content.isNotEmpty()) return
        val skin = c.resources?.openRawResource(R.raw.block_skin_8x8)
        val png = BitmapFactory.decodeStream(skin)
        val skinsbw = Array(16) { i ->
            Matrix(
                Array(64) { j ->
                    if (png.getPixel(i % 4 * 8 + j % 8, i / 4 * 8 + j / 8).toUInt() != Color.Named.PINK().toUInt()) {
                        png.getPixel(i % 4 * 8 + j % 8, i / 4 * 8 + j / 8).toUInt().toColor()
                    } else
                        0x00000000u.toColor()
                },
                8,
                Color.Named.TRANS()
            )
        }

        for (n in Color.Named.values()) {
            content[n()] = mutableMapOf<FreeWays, Bitmap>()
            for (fw in FreeWays.values()) {
                var ix = 0
                if (fw[Dir2D.UP])
                    ix += 1
                if (fw[Dir2D.DOWN])
                    ix += 2
                if (fw[Dir2D.LEFT])
                    ix += 4
                if (fw[Dir2D.RIGHT])
                    ix += 8
                (content[n()] as MutableMap<FreeWays, Bitmap>)[fw] = Matrix(
                    Array(ViewDataContainer.factory.TileSize.area) { i -> n().and(skinsbw[ix][i]) },
                    ViewDataContainer.factory.TileSize.column,
                    Color.Named.TRANS()
                ).toBitmap()
            }
        }

    }

}