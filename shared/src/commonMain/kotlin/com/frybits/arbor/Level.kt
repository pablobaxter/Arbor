package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

sealed class Level(val logLevel: Int) {
    operator fun compareTo(level: Level): Int {
        return logLevel - level.logLevel
    }
}

object Error : Level(6)
object Warn : Level(5)
object Info : Level(4)
object Debug : Level(3)
object Verbose : Level(2)
