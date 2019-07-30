package boss.tetris.basics

enum class Direction1D(val vector: Position1D) {
    NONE(Position1D(0)),
    RIGHT(Position1D(1)),
    LEFT(Position1D(-1));


    val reverse: Direction1D
        get(){
            if (ordinal == 0)
                return NONE
            return values()[((ordinal + 1) % 2)+1]
        }

    fun valueOfShort(s: String): Direction1D {
        for (d in Direction1D.values()){
            if(d.name.startsWith(s.toUpperCase()))
                return d
        }
        return NONE
    }
}