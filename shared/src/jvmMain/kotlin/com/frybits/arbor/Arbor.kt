package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

object Arbor {

    fun addBranch(branch: Branch) {
        sharedAddBranch(branch)
    }

    fun removeBranch(branch: Branch) {
        sharedRemoveBranch(branch)
    }

    fun clear() {
        sharedClear()
    }

    fun log(level: Level, tag: String, message: String?, throwable: Throwable?) {
        sharedLog(level, tag, message, throwable)
    }

    fun e(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Error, tag, message, throwable)

    fun w(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Warn, tag, message, throwable)

    fun i(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Info, tag, message, throwable)

    fun d(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Debug, tag, message, throwable)

    fun v(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Verbose, tag, message, throwable)
}
