package com.frybits.arbor

import kotlin.native.concurrent.ThreadLocal

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

//Needed for K/N coroutines
@ThreadLocal
object Arbor {

    private val branchLoggingContainer = ArborLoggingContainer()

    fun addBranch(branch: Branch) {
        branchLoggingContainer.submit(Add(branch))
    }

    fun removeBranch(branch: Branch) {
        branchLoggingContainer.submit(Remove(branch))
    }

    fun clear() {
        branchLoggingContainer.submit(Clear)
    }

    //Only visible for testing
    internal fun branchCount(): Int = branchLoggingContainer.branchCount()

    fun log(level: Level, tag: String, message: String? = null, throwable: Throwable? = null) {
        branchLoggingContainer.submit(Log(level, tag, message, throwable))
    }

    fun e(tag: String, message: String? = null, throwable: Throwable? = null) = log(Error, tag, message, throwable)

    fun w(tag: String, message: String? = null, throwable: Throwable? = null) = log(Warn, tag, message, throwable)

    fun i(tag: String, message: String? = null, throwable: Throwable? = null) = log(Info, tag, message, throwable)

    fun d(tag: String, message: String? = null, throwable: Throwable? = null) = log(Debug, tag, message, throwable)

    fun v(tag: String, message: String? = null, throwable: Throwable? = null) = log(Verbose, tag, message, throwable)
}
