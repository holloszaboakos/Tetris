package boss.tetris.basics

data class Position1D(var column: Int = 0) {

    operator fun plus(p: Position1D): Position1D {
        return Position1D(column + p.column)
    }

    operator fun times(i: Int): Position1D {
        return Position1D(column * i)
    }

    override fun toString(): String {
        return "($column)"
    }

    companion object {
        fun valueOf(s: String): Position1D {
            if (!("^\\(\\d+\\)$".toRegex().matches(s)))
                throw  Exception("$s is not in the correct coord format")
            val column:Int = s.substring(1, s.length-1).toInt()
            return Position1D(column)
        }
    }
}