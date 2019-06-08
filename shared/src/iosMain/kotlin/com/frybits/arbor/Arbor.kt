package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

actual fun addBranch(branch: Branch) {
    _addBranch(branch)
}

actual fun removeBranch(branch: Branch) {
    _removeBranch(branch)
}

actual fun clear() {
    _clear()
}

actual fun log(level: Level, tag: String, message: String?, throwable: Throwable?) {
    _log(level, tag, message, throwable)
}

actual fun e(tag: String, message: String?, throwable: Throwable?) = log(Level.Error, tag, message, throwable)

actual fun w(tag: String, message: String?, throwable: Throwable?) = log(Level.Warn, tag, message, throwable)

actual fun i(tag: String, message: String?, throwable: Throwable?) = log(Level.Info, tag, message, throwable)

actual fun d(tag: String, message: String?, throwable: Throwable?) = log(Level.Debug, tag, message, throwable)

actual fun v(tag: String, message: String?, throwable: Throwable?) = log(Level.Verbose, tag, message, throwable)
