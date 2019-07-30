package boss.tetris

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import boss.tetris.graphics.bitmap.SkinStorage
import boss.tetris.model.Stack
import boss.tetris.model.TickerClock
import kotlinx.android.synthetic.main.activity_start_menu.*


@ExperimentalUnsignedTypes
class StartMenu : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        SkinStorage.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_menu)
        startB.setOnClickListener {
            val i = Intent(this, ToughnessChooser::class.java)
            startActivity(i)
        }

        continueB.setOnClickListener {
            val i = Intent(this, GameSurface::class.java)
            startActivity(i)
        }

    }
}
