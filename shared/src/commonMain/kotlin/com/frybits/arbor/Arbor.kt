package com.frybits.arbor

import kotlin.jvm.JvmSynthetic

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

expect object Arbor {

    fun addBranch(branch: Branch)

    fun removeBranch(branch: Branch)

    fun clear()

    @JvmSynthetic
    internal fun branchCount(): Int

    fun log(level: Level, tag: String, message: String? = null, throwable: Throwable? = null)

    fun e(tag: String, message: String? = null, throwable: Throwable? = null)

    fun w(tag: String, message: String? = null, throwable: Throwable? = null)

    fun i(tag: String, message: String? = null, throwable: Throwable? = null)

    fun d(tag: String, message: String? = null, throwable: Throwable? = null)

    fun v(tag: String, message: String? = null, throwable: Throwable? = null)
}
