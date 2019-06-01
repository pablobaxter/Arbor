package com.frybits.arbor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_queue_create
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal actual object ArborDispatchers {

    internal actual val Logger: CoroutineDispatcher = NsQueueDispatcher(dispatch_queue_create("ArborQueue", null))

}

internal class NsQueueDispatcher(private val dispatchQueue: dispatch_queue_t) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}
