package com.frybits.arbor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

actual abstract class Branch actual constructor(level: Level, vararg filterTags: String) {

    private val logChannel = Channel<Action>(Channel.UNLIMITED)

    @JvmSynthetic
    internal actual val logLevel: Level = level

    @JvmSynthetic
    internal actual val filterTagSet = setOf(*filterTags)

    private var isPlanted = false

    init {
        GlobalScope.launch(Dispatchers.Default) {
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

    actual abstract fun onAdd()

    actual abstract fun onRemove()

    actual abstract fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?)

    protected fun finalize() {
        logChannel.close()
    }
}
