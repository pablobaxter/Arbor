package com.frybits.arbor.ios

import com.frybits.arbor.*
import platform.Foundation.NSLog

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

class ConsoleBranch(level: Level = Info, vararg tags: String) : Branch(level, *tags) {

    override fun onAdd() {
        //Do nothing
    }

    override fun onRemove() {
        //Do nothing
    }

    override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
        when (level) {
            Verbose, Debug, Info -> {
                println("${level::class.simpleName}::$tag::${message ?: ""}")
                throwable?.printStackTrace()
            }
            Warn, Error -> {
                NSLog("${level::class.simpleName}::$tag::${message ?: ""}\n")
                throwable?.printStackTrace()
            }
        }
    }
}
