package boss.tetris

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import boss.tetris.R
import boss.tetris.model.Stack
import boss.tetris.model.TickerClock
import kotlinx.android.synthetic.main.activity_toughness_chooser.*

class ToughnessChooser : AppCompatActivity() {

    @ExperimentalUnsignedTypes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_toughness_chooser)
        infiniteB.setOnClickListener {
            Stack.speeding=0
            Stack.reset()
            Thread.sleep(100)
            TickerClock.time = TickerClock.defTime
            val i = Intent(this, GameSurface::class.java)
            startActivity(i)
        }
        easyB.setOnClickListener {
            Stack.speeding=2
            Stack.reset()
            Thread.sleep(100)
            TickerClock.time = TickerClock.defTime
            val i = Intent(this, GameSurface::class.java)
            startActivity(i)
        }
        normalB.setOnClickListener {
            Stack.speeding=4
            Stack.reset()
            Thread.sleep(100)
            TickerClock.time = TickerClock.defTime
            val i = Intent(this, GameSurface::class.java)
            startActivity(i)
        }
        hardB.setOnClickListener {
            Stack.speeding=8
            Stack.reset()
            Thread.sleep(100)
            TickerClock.time = TickerClock.defTime
            val i = Intent(this, GameSurface::class.java)
            startActivity(i)
        }
        survivalB.setOnClickListener {
            Stack.speeding=16
            Stack.reset()
            Thread.sleep(100)
            TickerClock.time = TickerClock.defTime
            val i = Intent(this, GameSurface::class.java)
            startActivity(i)
        }
    }
}
