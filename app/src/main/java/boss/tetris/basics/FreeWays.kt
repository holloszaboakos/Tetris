package boss.tetris.basics

import boss.tetris.basics.Direction2D.*


enum class FreeWays(
    up: Boolean,
    right: Boolean,
    down: Boolean,
    left: Boolean
) {
    ISOLATED(false, false, false, false),
    UP_OPEN(true, false, false, false),
    RIGHT_OPEN(false, true, false, false),
    DOWN_OPEN(false, false, true, false),
    LEFT_OPEN(false, false, false, true),
    UP_AND_RIGHT_OPEN(true, true, false, false),
    DOWN_AND_RIGHT_OPEN(false, true, true, false),
    DOWN_AND_LEFT_OPEN(false, false, true, true),
    UP_AND_LEFT_OPEN(true, false, false, true),
    VERTICAL_OPEN(true, false, true, false),
    HORIZONTAL_OPEN(false, true, false, true),
    UP_CLOSED(false, true, true, true),
    RIGHT_CLOSED(true, false, true, true),
    DOWN_CLOSED(true, true, false, true),
    LEFT_CLOSED(true, true, true, false),
    FULL_OPEN(true, true, true, true);

    private val isOpenDirection = mapOf(
        UP to up,
        RIGHT to right,
        DOWN to down,
        LEFT to left
    )

    operator fun get(direction: Direction2D) = isOpenDirection.getValue(direction)

    private val oneChange = mutableMapOf<Direction2D, FreeWays>()

    operator fun get(direction: Direction2D, isOpen: Boolean): FreeWays {
        when {
            isOpenDirection[direction] == isOpen -> return this
            direction in oneChange.keys -> return oneChange[direction] as FreeWays
            else -> {
                for (freeWays in values()) {
                    val failed = Direction2D.values()
                        .slice(1..4)
                        .any {
                            (it == direction && freeWays.isOpenDirection[it] != isOpen) ||
                                    (it != direction && freeWays.isOpenDirection[it] != isOpenDirection[it])
                        }

                    if (!failed) {
                        oneChange[direction] = freeWays
                        return freeWays
                    }
                }
                throw Exception("No GridConnection is different in that wall only")
            }
        }
    }

    private val oneRotation = mutableMapOf<Dir1D, FreeWays>()
    operator fun get(direction: Dir1D): FreeWays {
        if (direction in oneRotation.keys)
            return oneRotation.getValue(direction)
        else
            for (value in values()) {
                val failed = Direction2D.values().slice(1..4).any {di->
                    isOpenDirection[di] != value.isOpenDirection[di.rot(direction)]
                }
                if (!failed) {
                    oneRotation[direction] = value
                    return value
                }
            }
        throw Exception("No GridConnection is different in that wall only")
    }
}