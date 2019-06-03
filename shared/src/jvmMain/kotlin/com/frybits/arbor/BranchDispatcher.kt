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

internal actual class BranchDispatcher actual constructor(branch: Branch) {

    private val logChannel = Channel<Action>(Channel.UNLIMITED)

    private var isPlanted = false

    init {
        GlobalScope.launch(Dispatchers.Default) {
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
}
