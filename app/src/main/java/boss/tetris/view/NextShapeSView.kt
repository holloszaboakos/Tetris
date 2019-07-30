package boss.tetris.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceView
import boss.tetris.basics.FreeWays
import boss.tetris.basics.ThreadHolder
import boss.tetris.graphics.bitmap.BRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.graphics.bitmap.SkinStorage
import boss.tetris.graphics.bitmap.toUInt
import boss.tetris.model.Stack
import kotlin.concurrent.thread

@ExperimentalUnsignedTypes
class NextShapeSView : SurfaceView {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var loader = ThreadHolder("LOADER", false)

    init {
        loader.set {
            thread {
                val p = Paint()
                p.color = Color.Named.BLUE().toUInt().toInt()
                p.style = Paint.Style.FILL
                var canvas: Canvas? = null


                while (loader.run) {
                    var counter = 0
                    while (!holder.surface.isValid && loader.run) {
                        Thread.sleep(100)
                        counter++
                        if (counter >= 10)
                            println("Freeeeeze!!+${loader.id}")
                        if (counter >= 100)
                            break
                    }
                    if (!holder.surface.isValid)
                        println("Is not Valid!!!")
                    if (holder.surface.isValid) {
                        try {
                            synchronized(holder) {
                                canvas = holder.lockCanvas()
                            }

                            try {
                                canvas?.drawARGB(255, 63, 63, 63)
                                val smaller = canvas?.width ?: 0
                                val r = Rect(0, 0, smaller, smaller)
                                canvas?.drawBitmap(
                                    (SkinStorage.content[Color.Named.GRAY()] as Map<FreeWays, Bitmap>)[FreeWays.IS] as Bitmap,
                                    null,
                                    r,
                                    null
                                )

                                val shapeRep = (Stack.shape.rep as BRepresentation).rep
                                for (col in 0 until shapeRep.size.column)
                                    for (lin in 0 until shapeRep.size.line) {
                                        val rect = Rect(
                                            (col) * smaller / 5 + (smaller - smaller / 5 * shapeRep.size.column) / 2,
                                            (lin) * smaller / 5 + (smaller - smaller / 5 * shapeRep.size.line) / 2,
                                            (col + 1) * smaller / 5 + (smaller - smaller / 5 * shapeRep.size.column) / 2,
                                            (lin + 1) * smaller / 5 + (smaller - smaller / 5 * shapeRep.size.line) / 2
                                        )
                                        canvas?.drawBitmap(shapeRep[col, lin], null, rect, null)
                                    }

                            } catch (e: Exception) {
                                print("stackView" + e.message + loader.id)
                            }

                        } finally {
                            if (canvas != null)
                                synchronized(holder) {
                                    holder.unlockCanvasAndPost(canvas)
                                }
                        }
                    }
                }
            }
        }
    }
}