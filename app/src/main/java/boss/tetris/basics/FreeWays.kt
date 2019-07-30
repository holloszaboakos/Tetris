package boss.tetris.basics

import java.lang.Exception

val T = true
val F = false

enum class FreeWays(
    up: Boolean,
    right: Boolean,
    down: Boolean,
    left: Boolean
) {
    IS(F, F, F, F),
    UP(T, F, F, F),
    RI(F, T, F, F),
    DO(F, F, T, F),
    LE(F, F, F, T),
    UR(T, T, F, F),
    DR(F, T, T, F),
    DL(F, F, T, T),
    UL(T, F, F, T),
    VE(T, F, T, F),
    HO(F, T, F, T),
    NU(F, T, T, T),
    NR(T, F, T, T),
    ND(T, T, F, T),
    NL(T, T, T, F),
    AL(T, T, T, T);

    private val ways = mapOf(
        Pair(Dir2D.UP, up),
        Pair(Dir2D.RIGHT, right),
        Pair(Dir2D.DOWN, down),
        Pair(Dir2D.LEFT, left)
    )
    operator fun get(d: Dir2D) = ways[d] as Boolean

    private val oneChange = mutableMapOf<Dir2D, FreeWays>()
    operator fun get(d: Dir2D, b: Boolean): FreeWays {
        when {
            ways[d] == b -> return this
            d in oneChange.keys -> return oneChange[d] as FreeWays
            else -> {
                for (gc in values()) {
                    var failed = false
                    for (id in 1..4) {
                        val di = Dir2D.values()[id]
                        if (di == d && gc.ways[di] != b)
                            failed = true
                        else if (di != d && gc.ways[di] != ways[di])
                            failed = true
                    }
                    if (!failed) {
                        oneChange[d] = gc
                        return gc
                    }
                }
                throw Exception("No GridConnection is different in that wall only")
            }
        }
    }

    private val oneRot = mutableMapOf<Dir1D, FreeWays>()
    operator fun get(d: Dir1D) : FreeWays {
        if (d in oneRot.keys)
            return oneRot[d] as FreeWays
        else
            for (gc in values()) {
                var failed = false
                for (id in 1..4) {
                    val di = Dir2D.values()[id]
                    if (ways[di] != gc.ways[di.rot(d)])
                        failed = true
                }
                if (!failed) {
                    oneRot[d] = gc
                    return gc
                }
            }
        throw Exception("No GridConnection is different in that wall only")
    }
}