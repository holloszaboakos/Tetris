package boss.tetris.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import boss.tetris.basics.LifeCicle
import boss.tetris.basics.Position
import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.ColorH
import boss.tetris.graphics.bitmap.tiny.BTGraphicFactory
import boss.tetris.graphics.bitmap.toColor
import boss.tetris.model.Stack

@ExperimentalUnsignedTypes
class MySurface : SurfaceView {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init{BTGraphicFactory.GraphicLoader=this
         }

    val cycle: LifeCicle = object : LifeCicle() {
        val ms: MySurface = this@MySurface
        override fun createInner() {
            justStart = true
            graphicUpdater = GraphicUpdaterThread(ms)
        }

        override fun destroyInner() {
            graphicUpdater = null
        }

        override fun startInner() {
            if (graphicUpdater?.isAlive == false)
                graphicUpdater?.start()
        }

        override fun stopInner() {
            var retryp = true
            while (retryp) {
                try {
                    graphicUpdater?.Notify("Stop")
                    graphicUpdater?.join()
                    retryp = false
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            System.out.println("Stopped!!")
        }

        override fun pauseInner() {
        }

        override fun resumeInner() {
            graphicUpdater?.Notify("Resume")
        }
    }

    init {
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                // empty
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                //empty
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                //empty
            }
        })
    }


    var graphicUpdater: GraphicUpdaterThread? = null
    var justStart = true


    val tileSizeInPixel = BTGraphicFactory.TileSize
    val pictureSizeInTile: Position = Stack.size

    val picture: EasyWeightMap<ColorH> = EasyWeightMap(
        pictureSizeInTile.column * tileSizeInPixel.column,
        pictureSizeInTile.line * tileSizeInPixel.line,
        Color.TRANSPARENT.toUInt().toColor()
    )



    @Synchronized
    override fun invalidate() {
        graphicUpdater?.Notify("BitLoader:Invalidate")
    }

}