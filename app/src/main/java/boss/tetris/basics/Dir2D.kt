package boss.tetris.basics

enum class Direction2D(val vector: Position2D) {
    NONE(Position2D(0, 0)),
    UP(Position2D(0, -1)),
    RIGHT(Position2D(1, 0)),
    DOWN(Position2D(0, 1)),
    LEFT(Position2D(-1, 0));


    val reverse: Direction2D
        get() {
            if (ordinal == 0)
                return NONE
            return values()[((ordinal + 1) % 4) + 1]
        }

    fun rot(d: Direction1D) =
        when (d) {
            Direction1D.LEFT -> when (this) {
                UP -> LEFT
                LEFT -> DOWN
                DOWN -> RIGHT
                RIGHT -> UP
                NONE -> NONE
            }
            Direction1D.RIGHT -> when (this) {
                UP -> LEFT
                LEFT -> DOWN
                DOWN -> RIGHT
                RIGHT -> UP
                NONE -> NONE
            }
            Direction1D.NONE -> this
        }

    fun valueOfShort(s: String): Direction2D {
        for (d in values()) {
            if (d.name.startsWith(s.toUpperCase()))
                return d
        }
        return NONE
    }
}