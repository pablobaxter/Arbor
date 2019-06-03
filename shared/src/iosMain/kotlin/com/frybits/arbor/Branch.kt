package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

actual abstract class Branch actual constructor(level: Level, vararg filterTags: String) {

    internal actual val logLevel: Level
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    internal actual val filterTagSet: Set<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    actual abstract fun onAdd()
    actual abstract fun onRemove()
    actual abstract fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?)
}
