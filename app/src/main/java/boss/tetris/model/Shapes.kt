package boss.tetris.model

import boss.tetris.basics.Dir1D
import boss.tetris.basics.Direction2D
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
            FW.DOWN_OPEN,
            FW.VERTICAL_OPEN,
            FW.VERTICAL_OPEN,
            FW.UP_OPEN ),
            1, FW.ISOLATED),
        Color.Named.BLUE()
    ),
    SQUARE(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.DOWN_AND_RIGHT_OPEN, FW.DOWN_AND_LEFT_OPEN,
            FW.UP_AND_RIGHT_OPEN, FW.UP_AND_LEFT_OPEN ),
            2, FW.ISOLATED),
        Color.Named.RED()
    ),
    T_LETTER(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.RIGHT_OPEN, FW.UP_CLOSED, FW.LEFT_OPEN,
            FW.ISOLATED, FW.UP_OPEN, FW.ISOLATED ),
            3, FW.ISOLATED),
        Color.Named.GREEN()
    ),
    L_LETTER(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.ISOLATED, FW.DOWN_OPEN,
            FW.ISOLATED, FW.VERTICAL_OPEN,
            FW.RIGHT_OPEN, FW.UP_AND_LEFT_OPEN),
            2, FW.ISOLATED),
        Color.Named.YELLOW()
    ),
    L_REVERSE(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.DOWN_OPEN, FW.ISOLATED,
            FW.VERTICAL_OPEN, FW.ISOLATED,
            FW.UP_AND_RIGHT_OPEN, FW.LEFT_OPEN),
            2, FW.ISOLATED),
        Color.Named.ORANGE()
    ),
    Z_LETTER(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.RIGHT_OPEN, FW.DOWN_AND_LEFT_OPEN, FW.ISOLATED,
            FW.ISOLATED, FW.UP_AND_RIGHT_OPEN, FW.LEFT_OPEN),
            3, FW.ISOLATED),
        Color.Named.CIAN()
    ),
    Z_REVERSE(
        ViewDataContainer.factory.ShapeRep,
        Matrix(arrayOf(
            FW.ISOLATED, FW.DOWN_AND_RIGHT_OPEN, FW.LEFT_OPEN,
            FW.RIGHT_OPEN, FW.UP_AND_LEFT_OPEN, FW.ISOLATED),
            3, FW.ISOLATED),
        Color.Named.PURPLE()
    );

    init {
        rep.owner = this
    }

    var stateCounter: Long = 0

    var direction = Direction2D.UP
        set(value) {
            field = value;stateCounter++
        }

    val lookMap = mapOf(
        Pair(Direction2D.UP, lookUp),
        Pair(Direction2D.RIGHT, lookUp[Dir1D.RIGHT]),
        Pair(Direction2D.DOWN, lookUp[Dir1D.RIGHT][Dir1D.RIGHT]),
        Pair(Direction2D.LEFT, lookUp[Dir1D.LEFT])
    )

    val Look get() = lookMap[direction] as Matrix<FW>


    init {
        for( i in 0 until (lookMap[Direction2D.RIGHT] as Matrix).size.area)
            (lookMap[Direction2D.RIGHT] as Matrix<FW>)[i]= (lookMap[Direction2D.RIGHT] as Matrix<FW>)[i][Dir1D.RIGHT]
        for( i in 0 until (lookMap[Direction2D.DOWN] as Matrix).size.area)
            (lookMap[Direction2D.DOWN] as Matrix<FW>)[i]= (lookMap[Direction2D.DOWN] as Matrix<FW>)[i][Dir1D.RIGHT][Dir1D.RIGHT]
        for( i in 0 until (lookMap[Direction2D.LEFT] as Matrix).size.area)
            (lookMap[Direction2D.LEFT] as Matrix<FW>)[i]= (lookMap[Direction2D.LEFT] as Matrix<FW>)[i][Dir1D.LEFT]
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