package com.frybits.arbor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal actual class ArborLoggingContainer actual constructor() {

    private val updateChannel = Channel<Action>(Channel.UNLIMITED)
    private val branchCount = AtomicInteger(0)
    private val coroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    init {
        GlobalScope.launch(coroutineDispatcher) {
            val branches = mutableSetOf<Branch>()
            for (action in updateChannel) {
                when (action) {
                    is Add -> {
                        if (branches.add(action.branch)) {
                            action.branch.notifyAdd()
                            branchCount.incrementAndGet()
                        }
                    }
                    is Remove -> {
                        if (branches.remove(action.branch)) {
                            action.branch.notifyRemove()
                            branchCount.decrementAndGet()
                        }
                    }
                    is Log -> {
                        branches.forEach {
                            it.sendLog(action)
                        }
                    }
                    Clear -> {
                        branches.forEachThenRemove {
                            it.notifyRemove()
                        }
                        branchCount.set(0)
                    }
                }
            }
        }
    }

    @JvmSynthetic
    internal actual fun submit(action: Action) {
        updateChannel.offer(action)
    }

    @JvmSynthetic
    internal actual fun branchCount(): Int {
        return branchCount.get()
    }
}
