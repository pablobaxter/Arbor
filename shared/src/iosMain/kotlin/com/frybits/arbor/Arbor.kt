package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

//Shared function calls
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

//All other extras for iOS calls

//Log
fun log(level: Level, tag: String, message: String?) = log(level, tag, message, null)

fun log(level: Level, tag: String, throwable: Throwable?) = log(level, tag, null, throwable)

//Error
fun e(tag: String, message: String?) = e(tag, message, null)

fun e(tag: String, throwable: Throwable?) = e(tag, null, throwable)

fun e(tag: String, message: String?, throwable: Throwable?) = log(Level.Error, tag, message, throwable)

//Warn
fun w(tag: String, message: String?) = w(tag, message, null)

fun w(tag: String, throwable: Throwable?) = w(tag, null, throwable)

fun w(tag: String, message: String?, throwable: Throwable?) = log(Level.Warn, tag, message, throwable)

//Info
fun i(tag: String, message: String?) = i(tag, message, null)

fun i(tag: String, throwable: Throwable?) = i(tag, null, throwable)

fun i(tag: String, message: String?, throwable: Throwable?) = log(Level.Info, tag, message, throwable)

//Debug
fun d(tag: String, message: String?) = d(tag, message, null)

fun d(tag: String, throwable: Throwable?) = d(tag, null, throwable)

fun d(tag: String, message: String?, throwable: Throwable?) = log(Level.Debug, tag, message, throwable)

//Verbose
fun v(tag: String, message: String?) = v(tag, message, null)

fun v(tag: String, throwable: Throwable?) = v(tag, null, throwable)

fun v(tag: String, message: String?, throwable: Throwable?) = log(Level.Verbose, tag, message, throwable)
