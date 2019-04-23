package boss.tetris.model

import android.graphics.Color
import boss.tetris.basics.Direction
import boss.tetris.graphics.EasyWeightMap
import boss.tetris.graphics.bitmap.tiny.BTShapeRepresentation

const val O = false
const val X = true

enum class Shapes(
    val representation: BTShapeRepresentation,
    lookUp: EasyWeightMap<Boolean>,
    lookRight: EasyWeightMap<Boolean>,
    lookDown: EasyWeightMap<Boolean>,
    lookLeft: EasyWeightMap<Boolean>,
    val color: Int
) {
    LONG(
        BTShapeRepresentation(),
        EasyWeightMap(Array(4) { X }, 1, O),
        EasyWeightMap(Array(4) { X }, 4, O),
        EasyWeightMap(Array(4) { X }, 1, O),
        EasyWeightMap(Array(4) { X }, 4, O),
        Color.BLUE
    ),
    SQUARE(
        BTShapeRepresentation(),
        EasyWeightMap(Array(4) { X }, 2, O),
        EasyWeightMap(Array(4) { X }, 2, O),
        EasyWeightMap(Array(4) { X }, 2, O),
        EasyWeightMap(Array(4) { X }, 2, O),
        Color.RED
    ),
    T_LETTER(
        BTShapeRepresentation(),
        EasyWeightMap(
            arrayOf(
                X, X, X,
                O, X, O
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                O, X,
                X, X,
                O, X
            ), 2, O
        ),
        EasyWeightMap(
            arrayOf(
                O, X, O,
                X, X, X
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                X, O,
                X, X,
                X, O
            ), 2, O
        ),
        Color.GREEN
    ),
    L_LETTER(
        BTShapeRepresentation(),
        EasyWeightMap(
            arrayOf(
                X, O,
                X, O,
                X, X
            ), 2, O
        ),
        EasyWeightMap(
            arrayOf(
                X, X, X,
                X, O, O
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                X, X,
                O, X,
                O, X
            ), 2, O
        ),
        EasyWeightMap(
            arrayOf(
                O, O, X,
                X, X, X
            ), 3, O
        ),
        Color.YELLOW
    ),
    L_REFLECT(
        BTShapeRepresentation(),
        EasyWeightMap(
            arrayOf(
                O, X,
                O, X,
                X, X
            ), 2, O
        ),
        EasyWeightMap(
            arrayOf(
                X, O, O,
                X, X, X
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                X, X,
                X, O,
                X, O
            ), 2, O
        ),
        EasyWeightMap(
            arrayOf(
                X, X, X,
                O, O, X
            ), 3, O
        ),
        0xFFFFA500.toInt()
    ),
    Z_LETTER(
        BTShapeRepresentation(),
        EasyWeightMap(
            arrayOf(
                X, X, O,
                O, X, X
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                O, X,
                X, X,
                X, O
            ), 2, O
        ),
        EasyWeightMap(
            arrayOf(
                X, X, O,
                O, X, X
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                O, X,
                X, X,
                X, O
            ), 2, O
        ),
        Color.LTGRAY
    ),
    Z_REVERSE(
        BTShapeRepresentation(),
        EasyWeightMap(
            arrayOf(
                O, X, X,
                X, X, O
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                X, O,
                X, X,
                O, X
            ), 2, O
        ),
        EasyWeightMap(
            arrayOf(
                O, X, X,
                X, X, O
            ), 3, O
        ),
        EasyWeightMap(
            arrayOf(
                X, O,
                X, X,
                O, X
            ), 2, O
        ),
        Color.DKGRAY
    );

    init {
        representation.owner = this
    }

    private val lookMap = mapOf(
        Pair(Direction.UP, lookUp),
        Pair(Direction.RIGHT, lookRight),
        Pair(Direction.DOWN, lookDown),
        Pair(Direction.LEFT, lookLeft)
    )
    private var direction = Direction.UP
    val Look get() = lookMap[direction] as EasyWeightMap<Boolean>
    fun rotate() {
        if (!Stack.isHit(
                lookMap[Direction.values()[direction.ordinal % 4 + 1]] as EasyWeightMap<Boolean>,
                Stack.shapePosition
            )
        )
            direction = Direction.values()[direction.ordinal % 4 + 1]
    }


}