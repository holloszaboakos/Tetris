package boss.tetris

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import boss.tetris.model.Stack
import boss.tetris.model.TickerClock
import kotlinx.android.synthetic.main.activity_game_ended.*

@ExperimentalUnsignedTypes
class GameEnded : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_ended)
        endPointTV.text=Stack.point.toString()
        RePlayB.setOnClickListener {
            TickerClock.time= TickerClock.defTime
            Stack.reset()
            val i= Intent(this,GameSurface::class.java)
            startActivity(i)
            finish()
        }
    }
    override fun onBackPressed()
    {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        this.startActivity(Intent(this,StartMenu::class.java))
        finish()
    }
}
