package boss.tetris.basics

enum class Dir1D(val vector: Vec1D) {
    NONE(Vec1D(0)),
    RIGHT(Vec1D(1)),
    LEFT(Vec1D(-1));


    val reverse: Dir1D
        get(){
            if (ordinal == 0)
                return NONE
            return values()[((ordinal + 1) % 2)+1]
        }

    fun valueOfShort(s: String): Dir1D {
        for (d in Dir1D.values()){
            if(d.name.startsWith(s.toUpperCase()))
                return d
        }
        return NONE
    }
}