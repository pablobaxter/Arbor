package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

private val branchLoggingContainer = ArborLoggingContainer()

internal fun _addBranch(branch: Branch) {
    branchLoggingContainer.submit(Add(branch))
}

internal fun _removeBranch(branch: Branch) {
    branchLoggingContainer.submit(Remove(branch))
}

internal fun _clear() {
    branchLoggingContainer.submit(Clear)
}

//Only visible for testing
internal fun _branchCount(): Int = branchLoggingContainer.branchCount()

internal fun _log(level: Level, tag: String, message: String? = null, throwable: Throwable? = null) {
    branchLoggingContainer.submit(Log(level, tag, message, throwable))
}
