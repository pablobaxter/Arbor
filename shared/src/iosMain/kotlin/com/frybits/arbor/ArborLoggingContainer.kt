package com.frybits.arbor

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import platform.darwin.dispatch_get_current_queue
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
        if (dispatch_get_current_queue() == dispatch_get_main_queue()) {
            handleAction(action)
        }
        GlobalScope.launch(Main) {
            handleAction(action)
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
                branches.removeForEach {
                    it.notifyRemove()
                }
                branchCount.value = 0
            }
        }
    }
}
