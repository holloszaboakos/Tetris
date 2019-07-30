package boss.tetris.basics

enum class Dir2D(val vector: Vec2D) {
    NONE(Vec2D(0, 0)),
    UP(Vec2D(0, -1)),
    RIGHT(Vec2D(1, 0)),
    DOWN(Vec2D(0, 1)),
    LEFT(Vec2D(-1, 0));


    val reverse: Dir2D
        get() {
            if (ordinal == 0)
                return NONE
            return values()[((ordinal + 1) % 4) + 1]
        }

    fun rot(d: Dir1D) =
        when (d) {
            Dir1D.LEFT -> when (this) {
                UP -> LEFT
                LEFT -> DOWN
                DOWN -> RIGHT
                RIGHT -> UP
                NONE -> NONE
            }
            Dir1D.RIGHT -> when (this) {
                UP -> RIGHT
                LEFT -> UP
                DOWN -> LEFT
                RIGHT -> DOWN
                NONE -> NONE
            }
            Dir1D.NONE -> this
        }

    fun valueOfShort(s: String): Dir2D {
        for (d in values()) {
            if (d.name.startsWith(s.toUpperCase()))
                return d
        }
        return NONE
    }
}