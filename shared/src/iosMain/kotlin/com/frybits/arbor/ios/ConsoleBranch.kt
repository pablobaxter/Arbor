package com.frybits.arbor.ios

import com.frybits.arbor.Branch
import com.frybits.arbor.Level
import platform.Foundation.NSLog

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

class ConsoleBranch : Branch {

    constructor() : super(Level.Info, listOf())

    constructor(level: Level = Level.Info) : super(level, listOf())

    constructor(tags: List<String>) : super(Level.Info, tags)

    constructor(level: Level, tags: List<String>) : super(level, tags)

    override fun onAdd() {
        //Do nothing
    }

    override fun onRemove() {
        //Do nothing
    }

    override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
        when (level) {
            Level.Verbose, Level.Debug, Level.Info -> {
                println("${level.name}::$tag::${message ?: ""}")
                throwable?.printStackTrace()
            }
            Level.Warn, Level.Error -> {
                NSLog("${level.name}::$tag::${message ?: ""}\n")
                throwable?.printStackTrace()
            }
        }
    }
}
