package boss.tetris

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.Window
import android.view.WindowManager
import boss.tetris.basics.Dir1D
import boss.tetris.basics.Dir2D
import boss.tetris.model.Stack
import boss.tetris.model.TickerClock
import boss.tetris.view.NextShapeSView
import boss.tetris.view.StackSView
import boss.tetris.view.ViewDataContainer
import kotlinx.android.synthetic.main.activity_game_surface.*

@ExperimentalUnsignedTypes
class GameSurface : AppCompatActivity(), SurfaceHolder.Callback {


    var stackView: StackSView? = null
    var nextShapeView: NextShapeSView? = null

    override fun surfaceCreated(holder: SurfaceHolder) {
        stackView?.loader?.start()
        nextShapeView?.loader?.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stackView?.loader?.stop()
        nextShapeView?.loader?.stop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        stackView?.loader?.stop()
        stackView?.loader?.start()
        nextShapeView?.loader?.stop()
        nextShapeView?.loader?.start()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stack.actualGS = this
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_game_surface)
        stackView = surfaceMS
        nextShapeView = nextShapeSV

        DownB.setOnClickListener {
            for (i in 0..3)
                Stack.tick()
        }
        LeftB.setOnClickListener {
            if (!Stack.isHit(Stack.shape.Look, Stack.shapePosition + Dir2D.LEFT.vector))
                Stack.shapePosition += Dir2D.LEFT.vector
        }
        RotRB.setOnClickListener {
            Stack.shape.rotate(Dir1D.RIGHT)
        }
        RotLB.setOnClickListener {
            Stack.shape.rotate(Dir1D.LEFT)
        }
        RightB.setOnClickListener {
            if (!Stack.isHit(Stack.shape.Look, Stack.shapePosition + Dir2D.RIGHT.vector))
                Stack.shapePosition += Dir2D.RIGHT.vector
        }
    }

    fun gameEnded() {
        runOnUiThread {
            TickerClock.stop()
            val i = Intent(this, GameEnded::class.java)
            startActivity(i)
            finish()
        }
    }

    fun setPoint(point: Int) {
        runOnUiThread {
            var tmp = point
            var text = "P\nO\nN\nT"
            while (tmp != 0) {
                text = (tmp % 10).toString() + "\n" + text
                tmp /= 10
            }
            PointTV.text = text
        }
    }

    override fun onBackPressed() {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        this.startActivity(Intent(this, StartMenu::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        TickerClock.start()
        surfaceCreated(stackView?.holder as SurfaceHolder)
    }

    override fun onStop() {
        surfaceDestroyed(stackView?.holder as SurfaceHolder)
        TickerClock.stop()
        super.onStop()
    }
}
