package com.frybits.arbor

import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.native.concurrent.AtomicInt

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

//TODO Warn users this runs everything in the main dispatch queue. Will be fixed later
internal actual class ArborLoggingContainer actual constructor() {

    private val branches = hashSetOf<Branch>()
    private val branchCount = AtomicInt(0)

    //TODO This should be changed when K/N coroutines fixes issues for native platform
    internal actual fun submit(action: Action) {
        if (testEnvironment) { //Test environment doesn't play well with async. This will be fixed when K/MPP is released
            handleAction(action)
        } else {
            dispatch_async(dispatch_get_main_queue()) {
                handleAction(action)
            }
        }
    }

    internal actual fun branchCount(): Int {
        return branchCount.value
    }

    private fun handleAction(action: Action) {
        when (action) {
            is Add -> {
                if (branches.add(action.branch)) {
                    action.branch.notifyAdd()
                    branchCount.increment()
                }
            }
            is Remove -> {
                if (branches.remove(action.branch)) {
                    action.branch.notifyRemove()
                    branchCount.decrement()
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
                branchCount.value = 0
            }
        }
    }
}
