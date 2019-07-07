package boss.tetris

import android.content.Intent
import android.graphics.Color
import android.graphics.Path
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import boss.tetris.basics.Direction
import boss.tetris.model.Stack
import boss.tetris.model.TickerClock
import boss.tetris.view.MySurface
import kotlinx.android.synthetic.main.activity_game_surface.*

@ExperimentalUnsignedTypes
class GameSurface : AppCompatActivity() {


    var view: MySurface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stack.actualGS = this
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_game_surface)
        view = surfaceMS

        TickerB.setOnClickListener {
            for (i in 0..3)
                Stack.tick()
        }
        LeftB.setOnClickListener {
            if (!Stack.isHit(Stack.shape.Look, Stack.shapePosition + Direction.LEFT.vector))
                Stack.shapePosition += Direction.LEFT.vector
        }
        TurnB.setOnClickListener {
            Stack.shape.rotate()
        }
        RightB.setOnClickListener {
            if (!Stack.isHit(Stack.shape.Look, Stack.shapePosition + Direction.RIGHT.vector))
                Stack.shapePosition += Direction.RIGHT.vector
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


    override fun onBackPressed()
    {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        this.startActivity(Intent(this,StartMenu::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        view?.cycle?.start()
        TickerClock.start()
    }

    override fun onStop() {
        TickerClock.stop()
        view?.cycle?.stop()

        //ModelContainer.fieldsMap.forEach { it.value.rep.modified = true }
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        view?.cycle?.resume()
    }

    override fun onPause() {
        view?.cycle?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        view?.cycle?.destroy()
        super.onDestroy()
    }
}
