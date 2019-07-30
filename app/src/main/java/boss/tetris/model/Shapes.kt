package boss.tetris.model

import boss.tetris.basics.Dir1D
import boss.tetris.basics.Dir2D
import boss.tetris.basics.FreeWays as FW
import boss.tetris.graphics.Matrix
import boss.tetris.graphics.base.ShapeRepresentation
import boss.tetris.graphics.bitmap.Color
import boss.tetris.view.ViewDataContainer


@ExperimentalUnsignedTypes
enum class Shapes(
    val rep: ShapeRepresentation,
    lookUp: Matrix<FW>,
    val color: Color
) {
    LONG(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.DO,
            FW.VE,
            FW.VE,
            FW.UP ),
            1, FW.IS),
        Color.Named.BLUE()
    ),
    SQUARE(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.DR, FW.DL,
            FW.UR, FW.UL ),
            2, FW.IS),
        Color.Named.RED()
    ),
    T_LETTER(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.RI, FW.NU, FW.LE,
            FW.IS, FW.UP, FW.IS ),
            3, FW.IS),
        Color.Named.GREEN()
    ),
    L_LETTER(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.IS, FW.DO,
            FW.IS, FW.VE,
            FW.RI, FW.UL),
            2, FW.IS),
        Color.Named.YELLOW()
    ),
    L_REVERSE(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.DO, FW.IS,
            FW.VE, FW.IS,
            FW.UR, FW.LE),
            2, FW.IS),
        Color.Named.ORANGE()
    ),
    Z_LETTER(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.RI, FW.DL, FW.IS,
            FW.IS, FW.UR, FW.LE),
            3, FW.IS),
        Color.Named.CIAN()
    ),
    Z_REVERSE(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.IS, FW.DR, FW.LE,
            FW.RI, FW.UL, FW.IS),
            3, FW.IS),
        Color.Named.PURPLE()
    );

    init {
        rep.owner = this
    }

    var stateCounter: Long = 0

    var direction = Dir2D.UP
        set(value) {
            field = value;stateCounter++
        }

    val lookMap = mapOf(
        Pair(Dir2D.UP, lookUp),
        Pair(Dir2D.RIGHT, lookUp[Dir1D.RIGHT]),
        Pair(Dir2D.DOWN, lookUp[Dir1D.RIGHT][Dir1D.RIGHT]),
        Pair(Dir2D.LEFT, lookUp[Dir1D.LEFT])
    )

    val Look get() = lookMap[direction] as Matrix<FW>


    init {
        for( i in 0 until (lookMap[Dir2D.RIGHT] as Matrix).size.area)
            (lookMap[Dir2D.RIGHT] as Matrix<FW>)[i]= (lookMap[Dir2D.RIGHT] as Matrix<FW>)[i][Dir1D.RIGHT]
        for( i in 0 until (lookMap[Dir2D.DOWN] as Matrix).size.area)
            (lookMap[Dir2D.DOWN] as Matrix<FW>)[i]= (lookMap[Dir2D.DOWN] as Matrix<FW>)[i][Dir1D.RIGHT][Dir1D.RIGHT]
        for( i in 0 until (lookMap[Dir2D.LEFT] as Matrix).size.area)
            (lookMap[Dir2D.LEFT] as Matrix<FW>)[i]= (lookMap[Dir2D.LEFT] as Matrix<FW>)[i][Dir1D.LEFT]
    }

    fun rotate(d:Dir1D) {
        if (!Stack.isHit(
                lookMap[direction.rot(d)] as Matrix<FW>,
                Stack.shapePosition
            )
        )
            direction = direction.rot(d)
        stateCounter++
    }


}