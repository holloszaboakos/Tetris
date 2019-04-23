package boss.tetris.basics

enum class Direction(val vector: Position) {
	NONE(Position(0,0)),UP(Position(0,-1)), RIGHT(Position(1,0)), DOWN(Position(0,1)), LEFT(Position(-1,0));


	val reverse: Direction
	get(){
		if (ordinal == 0)
			return NONE
		return values()[((ordinal + 1) % 4)+1]
	}

	fun valueOfShort(s: String): Direction {
		for (d in values()){
			if(d.name.startsWith(s.toUpperCase()))
				return d
		}
		return DOWN
	}
}