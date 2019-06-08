package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

abstract class Branch(private val logLevel: Level, filterTags: List<String> = listOf()) {

    private val filterTagSet = filterTags.toSet()
    private val branchDispatcher = BranchDispatcher(this)

    internal fun sendLog(log: Log) {
        branchDispatcher.submit(log)
    }

    internal fun notifyAdd() {
        branchDispatcher.submit(Add(this))
    }

    internal fun notifyRemove() {
        branchDispatcher.submit(Remove(this))
    }

    internal fun log(log: Log) {
        if (log.level >= logLevel && (filterTagSet.isEmpty()) || filterTagSet.contains(log.tag)) {
            onLog(log.level, log.tag, log.message, log.throwable)
        }
    }

    abstract fun onAdd()

    abstract fun onRemove()

    abstract fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?)
}
