package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

expect abstract class Branch(level: Level, vararg filterTags: String) {

    internal val logLevel: Level

    internal val filterTagSet: Set<String>

    abstract fun onAdd()

    abstract fun onRemove()

    abstract fun onLog(level: Level, tag: String, message: String? = null, throwable: Throwable? = null)
}
