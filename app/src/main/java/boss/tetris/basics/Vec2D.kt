package boss.tetris.basics

data class Position2D(var column: Int = 0, var line: Int = 0){

	operator fun get(dimension: Int): Int {
		return if (dimension % 2 == 0)
			column
		else
			line
	}

	val area get() = column*line

	operator fun set(dimension: Int, value: Int) {
		if (dimension % 2 == 0)
			column = value
		else
			line = value
	}

	operator fun plus(p: Position2D): Position2D {
		return Position2D(column + p.column, line + p.line)
	}

	operator fun times(i: Int): Position2D {
		return Position2D(column * i, line * i)
	}

	override fun toString(): String {
		return "($column;$line)"
	}

	companion object {
		fun valueOf(s: String): Position2D {
			if (!("^\\(\\d+;\\d+\\)$".toRegex().matches(s)))
				throw  Exception("$s is not in the correct coord format")
			val dividerIndex: Int = s.indexOf(';')
			val column:Int = s.substring(1, dividerIndex).toInt()
			val line:Int = s.substring(dividerIndex + 1, s.length-1).toInt()
			return Position2D(column, line)
		}
	}

}