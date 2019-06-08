package com.frybits.arbor.android

import android.util.Log
import com.frybits.arbor.Level

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

fun Level.toLogLevel(): Int = when (this) {
    Level.Error -> Log.ERROR
    Level.Warn -> Log.WARN
    Level.Info -> Log.INFO
    Level.Debug -> Log.DEBUG
    Level.Verbose -> Log.VERBOSE
}
