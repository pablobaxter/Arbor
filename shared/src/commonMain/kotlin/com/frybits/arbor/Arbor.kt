package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal expect fun addBranch(branch: Branch)

internal expect fun removeBranch(branch: Branch)

internal expect fun clear()

internal expect fun log(level: Level, tag: String, message: String? = null, throwable: Throwable? = null)

internal expect fun e(tag: String, message: String? = null, throwable: Throwable? = null)

internal expect fun w(tag: String, message: String? = null, throwable: Throwable? = null)

internal expect fun i(tag: String, message: String? = null, throwable: Throwable? = null)

internal expect fun d(tag: String, message: String? = null, throwable: Throwable? = null)

internal expect fun v(tag: String, message: String? = null, throwable: Throwable? = null)
