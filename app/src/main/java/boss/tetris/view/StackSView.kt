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
class StackSView : SurfaceView {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var loader = ThreadHolder("LOADER", false)

    init {
        ViewDataContainer.factory.graphicLoader = this
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
                                canvas?.drawARGB(255, 127, 127, 127)
                                val smaller = if (canvas?.width ?: 0 < (canvas?.height ?: 1 / 2))
                                    canvas?.width ?: 0
                                else
                                    (canvas?.height ?: 0) / 2
                                val r = Rect(0, 0, smaller, 2 * smaller)
                                canvas?.drawBitmap(
                                    (SkinStorage.content[Color.Named.GRAY()] as Map<FreeWays, Bitmap>)[FreeWays.AL] as Bitmap,
                                    null,
                                    r,
                                    null
                                )

                                val stackRep = (Stack.rep as BRepresentation).rep
                                for (col in 0 until Stack.size.column)
                                    for (lin in 0 until Stack.size.line) {
                                        val rect = Rect(
                                            col * smaller / 10,
                                            lin * smaller / 10,
                                            (col + 1) * smaller / 10,
                                            (lin + 1) * smaller / 10
                                        )
                                        canvas?.drawBitmap(stackRep[col, lin], null, rect, null)
                                    }

                                val shapeRep = (Stack.shape.rep as BRepresentation).rep
                                for (col in 0 until shapeRep.size.column)
                                    for (lin in 0 until shapeRep.size.line) {
                                        val rect = Rect(
                                            (Stack.shapePosition.column + col) * smaller / 10,
                                            (Stack.shapePosition.line + lin) * smaller / 10,
                                            (Stack.shapePosition.column + col + 1) * smaller / 10,
                                            (Stack.shapePosition.line + lin + 1) * smaller / 10
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