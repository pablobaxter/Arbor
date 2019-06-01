package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal sealed class Action

internal data class Add(val branch: Branch) : Action()
internal data class Remove(val branch: Branch) : Action()
internal data class Log(
        val level: Level,
        val tag: String,
        val message: String? = null,
        val throwable: Throwable? = null
) : Action()
