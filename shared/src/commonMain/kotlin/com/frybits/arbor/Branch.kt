package com.frybits.arbor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.jvm.JvmSynthetic

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

abstract class Branch(private val logLevel: Level, vararg filterTags: String) {

    private val logChannel = Channel<Action>(Channel.UNLIMITED)

    private val filterTagSet = setOf(*filterTags)

    private var isPlanted = false

    init {
        GlobalScope.launch(ArborDispatchers.Logger) {
            for (action in logChannel) {
                when (action) {
                    is Add -> {
                        withContext(Dispatchers.Main) {
                            onAdd()
                        }
                        isPlanted = true
                    }

                    is Remove -> {
                        isPlanted = false
                        withContext(Dispatchers.Main) {
                            onRemove()
                        }
                    }

                    is Log -> {
                        if (isPlanted) {
                            log(action)
                        }
                    }
                }
            }
        }
    }

    @JvmSynthetic
    internal fun sendLog(log: Log) {
        logChannel.offer(log)
    }

    @JvmSynthetic
    internal fun notifyAdd() {
        logChannel.offer(Add(this@Branch))
    }

    @JvmSynthetic
    internal fun notifyRemove() {
        logChannel.offer(Remove(this@Branch))
    }

    private fun log(log: Log) {
        if (log.level >= logLevel && (filterTagSet.isEmpty()) || filterTagSet.contains(log.tag)) {
            onLog(log.level, log.tag, log.message, log.throwable)
        }
    }

    abstract fun onAdd()

    abstract fun onRemove()

    abstract fun onLog(level: Level, tag: String, message: String? = null, throwable: Throwable? = null)

    protected fun finalize() {
        logChannel.close()
    }
}
