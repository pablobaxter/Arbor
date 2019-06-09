package com.frybits.arbor.ios

import com.frybits.arbor.Branch
import com.frybits.arbor.Level
import platform.Foundation.NSLog

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 *
 * iOS Console logging branch
 */

class ConsoleBranch : Branch {

    /**
     * Base constructor. Defaults to listen only to [Level.Info] logs and accepts all tags
     */
    constructor() : super(Level.Info, listOf())

    /**
     * Constructor accepting the [Level] to filter on. Accepts all tags
     *
     * @param level Allow all logs up to this level
     */
    constructor(level: Level) : super(level, listOf())

    /**
     * Constructor accepting a [List] of [String] tags to filter on. [Level] is defaulted to [Level.Info]
     *
     * @param tags List of strings that are expected. All other tags will be ignored
     */
    constructor(tags: List<String>) : super(Level.Info, tags)

    /**
     * Constructor accepting both a [level] and [tags] parameter. For allowing only selected logs
     *
     * @param level Allow all logs up to this level
     * @param tags List of strings that are expected. All other tags will be ignored
     */
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
