package boss.tetris.graphics.bitmap

@ExperimentalUnsignedTypes
fun UInt.toColor(): Color = Color(
    (this and 0xFF000000u).shr(24),
    (this and 0xFF0000u).shr(16),
    (this and 0xFF00u).shr(8),
    (this and 0xFFu)
)

@ExperimentalUnsignedTypes
fun Color.toUInt(): UInt =
    a.shl(24) +
            r.shl(16) +
            g.shl(8) +
            b


@ExperimentalUnsignedTypes
data class Color(
    var a: UInt = 0xFFu,
    var r: UInt = 0x00u,
    var g: UInt = 0x00u,
    var b: UInt = 0x00u
) {

    val id = nextId++

    companion object {
        var nextId = 0
    }

    enum class Named(private var value: Color) {
        TRANS(0x00000000u.toColor()),
        BLACK(0xFF000000u.toColor()),
        D_GRAY(0xFF3F3F3Fu.toColor()),
        GRAY(0xFF7F7F7Fu.toColor()),
        L_GRAY(0xFFBBBBBBu.toColor()),
        WHITE(0xFFFFFFFFu.toColor()),
        RED(0xFFFF0000u.toColor()),
        GREEN(0xFF00FF00u.toColor()),
        BLUE(0xFF0000FFu.toColor()),
        YELLOW(0xFFFFFF00u.toColor()),
        PURPLE(0xFFFF00FFu.toColor()),
        CIAN(0xFF00FFFFu.toColor()),
        D_CIAN(0xFF7FFFFFu.toColor()),
        L_CIAN(0xFF007F7Fu.toColor()),
        ORANGE(0xFFFF7F00u.toColor()),
        PINK(0xFFFF7F7Fu.toColor()),
        BROWN(0xFFFF7F3Fu.toColor()),
        ;
        operator fun invoke(): Color = value.copy()
    }

    operator fun plus(other: Color): Color {
        val result = this.copy()
        result.r = r * (0xFFu - other.a) / 0xFFu + other.r * other.a / 0xFFu
        result.g = g * (0xFFu - other.a) / 0xFFu + other.g * other.a / 0xFFu
        result.b = b * (0xFFu - other.a) / 0xFFu + other.b * other.a / 0xFFu
        result.a = if (a + other.a <= 0xFFu) a + other.a else 0xFFu
        return result
    }

    fun and(c: Color) = Color(
        a and c.a,
        r and c.r,
        g and c.g,
        b and c.b
    )

    operator fun minus(c: Color): Color = Color(
        if (a - c.a >= 0x00u) a - c.a else 0xFFu,
        if (r - c.r >= 0x00u) r - c.r else 0xFFu,
        if (g - c.g >= 0x00u) g - c.g else 0xFFu,
        if (b - c.b >= 0x00u) b - c.b else 0xFFu
    )

    operator fun times(a: UInt): Color {
        val tmp = this.copy()
        tmp.r = (this.r * a) / 0xFFu
        tmp.g = (this.g * a) / 0xFFu
        tmp.b = (this.b * a) / 0xFFu
        return tmp
    }

    fun copy(): Color = Color(a, r, g, b)


}