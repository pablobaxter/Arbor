package com.frybits.arbor

import kotlin.jvm.JvmSynthetic

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal sealed class Action

internal data class Add(@JvmSynthetic internal val branch: Branch) : Action()
internal data class Remove(@JvmSynthetic internal val branch: Branch) : Action()
internal object Clear : Action()
internal data class Log(
        @JvmSynthetic internal val level: Level,
        @JvmSynthetic internal val tag: String,
        @JvmSynthetic internal val message: String? = null,
        @JvmSynthetic internal val throwable: Throwable? = null
) : Action()
