package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

//The types of actions to submit to Arbor
internal sealed class Action

internal data class Add(internal val branch: Branch) : Action()
internal data class Remove(internal val branch: Branch) : Action()
internal object Clear : Action()
internal data class Log(
        internal val level: Level,
        internal val tag: String,
        internal val message: String? = null,
        internal val throwable: Throwable? = null
) : Action()
