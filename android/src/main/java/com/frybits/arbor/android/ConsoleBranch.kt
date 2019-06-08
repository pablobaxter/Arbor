package com.frybits.arbor.android

import android.util.Log
import com.frybits.arbor.Branch
import com.frybits.arbor.Level

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

class ConsoleBranch(level: Level = Level.Info, tags: List<String>) : Branch(level, tags) {

    override fun onAdd() {
        //Do nothing
    }

    override fun onRemove() {
        //Do nothing
    }

    override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
        if (throwable != null) {
            Log.println(level.toLogLevel(), tag, "${message ?: ""} \n ${Log.getStackTraceString(throwable)}")
        } else {
            if (message != null) {
                Log.println(level.toLogLevel(), tag, message)
            }
        }
    }
}
