package com.frybits.arbor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.Executors

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal actual class BranchDispatcher actual constructor(branch: Branch) {

    private val logChannel = Channel<Action>(Channel.UNLIMITED)
    private var isPlanted = false

    private val coroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val coroutineScope = CoroutineScope(coroutineDispatcher)

    init {
        coroutineScope.launch {
            for (action in logChannel) {
                when (action) {
                    is Add -> {
                        withContext(Dispatchers.Main) {
                            branch.onAdd()
                        }
                        isPlanted = true
                    }

                    is Remove -> {
                        isPlanted = false
                        withContext(Dispatchers.Main) {
                            branch.onRemove()
                        }
                    }

                    is Log -> {
                        if (isPlanted) {
                            branch.log(action)
                        }
                    }
                }
            }
        }
    }

    actual fun submit(action: Action) {
        logChannel.offer(action)
    }

    fun finalize() {
        coroutineDispatcher.close()
    }
}
