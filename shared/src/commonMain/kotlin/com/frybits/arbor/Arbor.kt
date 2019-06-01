package com.frybits.arbor

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

object Arbor {

    private val updateChannel = Channel<Action>(Channel.UNLIMITED)

    init {
        GlobalScope.launch(ArborDispatchers.Logger) {
            val branches = mutableSetOf<Branch>()
            for (action in updateChannel) {
                when (action) {
                    is Add -> {
                        action.branch.notifyAdd()
                        branches.add(action.branch)
                    }
                    is Remove -> {
                        branches.remove(action.branch)
                        action.branch.notifyRemove()
                    }
                    is Log -> {
                        branches.forEach {
                            it.sendLog(action)
                        }
                    }
                }
            }
        }
    }

    fun addBranch(branch: Branch) {
        updateChannel.offer(Add(branch))
    }

    fun removeBranch(branch: Branch) {
        updateChannel.offer(Remove(branch))
    }

    fun log(level: Level, tag: String, message: String? = null, throwable: Throwable? = null) {
        updateChannel.offer(Log(level, tag, message, throwable))
    }

    fun e(tag: String, message: String? = null, throwable: Throwable? = null) = log(Error, tag, message, throwable)

    fun w(tag: String, message: String? = null, throwable: Throwable? = null) = log(Warn, tag, message, throwable)

    fun i(tag: String, message: String? = null, throwable: Throwable? = null) = log(Info, tag, message, throwable)

    fun d(tag: String, message: String? = null, throwable: Throwable? = null) = log(Debug, tag, message, throwable)

    fun v(tag: String, message: String? = null, throwable: Throwable? = null) = log(Verbose, tag, message, throwable)
}
