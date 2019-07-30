package boss.tetris.basics

class ThreadHolder(val name: String, val silent: Boolean = true) {
    var thread: Thread? = null
    var run = true
    lateinit var creator: () -> Thread
    var id = 0


    fun set(c: () -> Thread) {
        creator = c
    }

    fun start() {
        run = true
        if (thread == null || thread?.isAlive == false){
            thread = creator()
            id++
        }
        if (!silent)
            println("Thread $name$id started!!!")
    }

    fun stop() {
        run = false
        thread?.join()
        thread = null
        if (!silent)
            println("Thread $name$id stopped!!!")
    }

    fun restart() {
        stop()
        start()
    }
}