package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

private val branchLoggingContainer = ArborLoggingContainer()

internal fun sharedAddBranch(branch: Branch) {
    branchLoggingContainer.submit(Add(branch))
}

internal fun sharedRemoveBranch(branch: Branch) {
    branchLoggingContainer.submit(Remove(branch))
}

internal fun sharedClear() {
    branchLoggingContainer.submit(Clear)
}

//Only visible for testing
internal fun sharedBranchCount(): Int = branchLoggingContainer.branchCount()

internal fun sharedLog(level: Level, tag: String, message: String? = null, throwable: Throwable? = null) {
    branchLoggingContainer.submit(Log(level, tag, message, throwable))
}
