package boss.tetris.model

import boss.tetris.basics.ThreadHolder
import kotlin.concurrent.thread

object TickerClock {
    private val ticker: ThreadHolder = ThreadHolder("TICKER",false)
    val defTime: Long = 512
    var time: Long = defTime
    val tickables = mutableListOf<tickable>()
    init {
        ticker.set {
            thread {
                while (ticker.run) {
                    for (tickable in tickables)
                        tickable.tick()
                    Thread.sleep(time)
                }
            }
        }
    }
    fun start() {
        ticker.start()
    }

    fun stop() {
        ticker.stop()
    }

    interface tickable {
        fun tick()
    }
}