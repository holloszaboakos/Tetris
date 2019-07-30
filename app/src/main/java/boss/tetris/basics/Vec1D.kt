package boss.tetris.basics

data class Vec1D(var column: Int = 0) {

    operator fun plus(p: Vec1D): Vec1D {
        return Vec1D(column + p.column)
    }

    operator fun times(i: Int): Vec1D {
        return Vec1D(column * i)
    }

    override fun toString(): String {
        return "($column)"
    }

    companion object {
        fun valueOf(s: String): Vec1D {
            if (!("^\\(\\d+\\)$".toRegex().matches(s)))
                throw  Exception("$s is not in the correct coord format")
            val column:Int = s.substring(1, s.length-1).toInt()
            return Vec1D(column)
        }
    }
}