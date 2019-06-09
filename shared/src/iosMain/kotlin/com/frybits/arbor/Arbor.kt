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

fun log(level: Level, tag: String, message: String?) {
    log(level, tag, message, null)
}

fun log(level: Level, tag: String, throwable: Throwable?) {
    log(level, tag, null, throwable)
}

actual fun log(level: Level, tag: String, message: String?, throwable: Throwable?) {
    _log(level, tag, message, throwable)
}

fun e(tag: String, message: String?) {
    e(tag, message, null)
}

fun e(tag: String, throwable: Throwable?) {
    e(tag, null, throwable)
}

actual fun e(tag: String, message: String?, throwable: Throwable?) = log(Level.Error, tag, message, throwable)

fun w(tag: String, message: String?) {
    w(tag, message, null)
}

fun w(tag: String, throwable: Throwable?) {
    w(tag, null, throwable)
}

actual fun w(tag: String, message: String?, throwable: Throwable?) = log(Level.Warn, tag, message, throwable)

fun i(tag: String, message: String?) {
    i(tag, message, null)
}

fun i(tag: String, throwable: Throwable?) {
    i(tag, null, throwable)
}

actual fun i(tag: String, message: String?, throwable: Throwable?) = log(Level.Info, tag, message, throwable)

fun d(tag: String, message: String?) {
    d(tag, message, null)
}

fun d(tag: String, throwable: Throwable?) {
    d(tag, null, throwable)
}

actual fun d(tag: String, message: String?, throwable: Throwable?) = log(Level.Debug, tag, message, throwable)

fun v(tag: String, message: String?) {
    v(tag, message, null)
}

fun v(tag: String, throwable: Throwable?) {
    v(tag, null, throwable)
}

actual fun v(tag: String, message: String?, throwable: Throwable?) = log(Level.Verbose, tag, message, throwable)
