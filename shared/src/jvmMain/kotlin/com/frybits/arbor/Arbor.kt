package com.frybits.arbor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

actual object Arbor {

    private val updateChannel = Channel<Action>(Channel.UNLIMITED)

    private val branchCount = AtomicInteger(0)

    init {
        GlobalScope.launch(Dispatchers.Default) {
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
                        branches.removeForEach {
                            it.notifyRemove()
                        }
                        branchCount.set(0)
                    }
                }
            }
        }
    }

    actual fun addBranch(branch: Branch) {
        updateChannel.offer(Add(branch))
    }

    actual fun removeBranch(branch: Branch) {
        updateChannel.offer(Remove(branch))
    }

    actual fun clear() {
        updateChannel.offer(Clear)
    }

    //Only visible for testing
    @JvmSynthetic
    internal actual fun branchCount(): Int = branchCount.get()

    actual fun log(level: Level, tag: String, message: String?, throwable: Throwable?) {
        updateChannel.offer(Log(level, tag, message, throwable))
    }

    actual fun e(tag: String, message: String?, throwable: Throwable?) = log(Error, tag, message, throwable)

    actual fun w(tag: String, message: String?, throwable: Throwable?) = log(Warn, tag, message, throwable)

    actual fun i(tag: String, message: String?, throwable: Throwable?) = log(Info, tag, message, throwable)

    actual fun d(tag: String, message: String?, throwable: Throwable?) = log(Debug, tag, message, throwable)

    actual fun v(tag: String, message: String?, throwable: Throwable?) = log(Verbose, tag, message, throwable)
}
