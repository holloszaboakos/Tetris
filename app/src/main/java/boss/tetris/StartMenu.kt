package boss.tetris

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import boss.tetris.model.Stack
import boss.tetris.model.TickerClock
import kotlinx.android.synthetic.main.activity_start_menu.*

class StartMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_menu)
        NewGameB.setOnClickListener {
            TickerClock.time=TickerClock.defTime
            Stack.reset()
            val i= Intent(this,GameSurface::class.java)
            startActivity(i)
        }

        ContGameB.setOnClickListener {
            val i= Intent(this,GameSurface::class.java)
            startActivity(i)
        }
    }
}
