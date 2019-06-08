package com.frybits.arbor.ios

import com.frybits.arbor.*
import platform.Foundation.NSLog

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

class ConsoleBranch(level: Level = Level.Info, tags: List<String> = listOf()) : Branch(level, tags) {

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
