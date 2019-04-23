package boss.tetris.model

import kotlin.concurrent.thread

object TickerClock {
    var run = false
    private var tickerThread:Thread?=null
    val defTime:Long = 500
    var time:Long = defTime
    val tickables = mutableListOf<tickable>()
    fun start() {
        run = true
        tickerThread=thread {
            while (run) {
                for (tickable in tickables)
                    tickable.tick()
                Thread.sleep(time)
            }
        }
    }
    fun stop(){
        run=false
        tickerThread?.join()
        tickerThread=null
    }
    interface tickable {
        fun tick()
    }
}