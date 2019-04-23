package boss.tetris.view

import android.graphics.*
import boss.tetris.basics.LifeState
import boss.tetris.basics.NotifiableThread
import boss.tetris.basics.Position
import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.tiny.BTBlockRepresentation
import boss.tetris.model.Stack

class GraphicUpdaterThread(private val owner: MySurface) : NotifiableThread("Updater") {

    private var ourRect = Rect()
    var p = Paint()
    var smaller: Int = 0

    override fun run() {
        super.run()

        var x = 0
        var canvas: Canvas? = null

        synchronized(owner.picture) {
            for (col in 0..owner.picture.columnSize) {
                for (lin in 0..owner.picture.lineSize) {
                    owner.picture[col, lin] = owner.picture.empty
                }
            }
        }

        while (owner.cycle.state == LifeState.Started || owner.cycle.state == LifeState.Resumed) {

            synchronized(owner.picture)
            {
                ////println("\n I jast started Updating!")

                for (lin in 0 until Stack.size.line)
                    for (col in 0 until Stack.size.column) {
                        owner.picture.drawOn(
                            (Stack.blockMap[lin][col].representation).representation,
                            Position(
                                col * owner.tileSizeInPixel.column,
                                lin * owner.tileSizeInPixel.line
                            )
                        )
                    }
                owner.picture.drawOn(Stack.shape.representation.representation,Stack.shapePosition*3)
            }

            synchronized(owner.picture)
            {
                var counter = 0
                while (!owner.holder.surface.isValid && counter != 200) {
                    Thread.sleep(200)
                    counter++
                    if (counter >= 100)
                        System.out.println("Freeeeeze!!")
                    if (counter >= 1000)
                        break
                }
                if (owner.holder.surface.isValid) {
                    try {
                        synchronized(owner.holder) {
                            canvas = owner.holder.lockCanvas()
                            while (canvas == null) {
                                System.out.println("canvas is null")
                                canvas = owner.holder.lockCanvas()
                            }
                        }
                        smaller = if (canvas?.width ?: 0 < (canvas?.height ?: 1 / 2))
                            canvas?.width ?: 0
                        else
                            (canvas?.height ?: 0) / 2

                        drawBackGround(canvas)

                        x++
                        paintIt(canvas)
                        if(x%1000==0)
                            println(x.toString())

                    } finally {
                        if (canvas != null) {
                            synchronized(owner.holder) {
                                owner.holder.unlockCanvasAndPost(canvas)
                            }
                        }
                    }
                }
            }
            //bufferLock.Wait()
            if (owner.cycle.state == LifeState.Started)
                bufferLock.Wait()
        }
    }

    private fun drawBackGround(canvas: Canvas?) {
        canvas?.drawARGB(255, 0, 0, 0)

        ourRect = Rect(0, 0, smaller, smaller)
        p = Paint()
        p.color = Color.BLUE
        p.style = Paint.Style.FILL
        canvas?.drawRect(ourRect, p)
    }

    private fun paintIt(canvas: Canvas?) {
        val bmp = Bitmap.createBitmap(owner.picture.columnSize, owner.picture.lineSize, Bitmap.Config.ARGB_8888)
        owner.picture.copyToBitmap(bmp)
        val canRect = Rect(0, 0, smaller, 2 * smaller)
        canvas?.drawBitmap(bmp, null, canRect, null)

    }


    private fun EasyWeightMap<Int>.copyToBitmap(bmp: Bitmap) {
        for (col in 0..(columnSize - 1))
            for (lin in 0..(lineSize - 1)) {
                bmp.setPixel(col, lin, this[col, lin])
            }
    }
}