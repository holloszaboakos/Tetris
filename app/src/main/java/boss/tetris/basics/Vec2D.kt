package boss.tetris.basics

data class Vec2D(var column: Int = 0, var line: Int = 0){

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

	operator fun plus(p: Vec2D): Vec2D {
		return Vec2D(column + p.column, line + p.line)
	}

	operator fun times(i: Int): Vec2D {
		return Vec2D(column * i, line * i)
	}

	override fun toString(): String {
		return "($column;$line)"
	}

	companion object {
		fun valueOf(s: String): Vec2D {
			if (!("^\\(\\d+;\\d+\\)$".toRegex().matches(s)))
				throw  Exception("$s is not in the correct coord format")
			val dividerIndex: Int = s.indexOf(';')
			val column:Int = s.substring(1, dividerIndex).toInt()
			val line:Int = s.substring(dividerIndex + 1, s.length-1).toInt()
			return Vec2D(column, line)
		}
	}

}