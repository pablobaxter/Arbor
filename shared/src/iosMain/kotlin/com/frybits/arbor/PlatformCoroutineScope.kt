package com.frybits.arbor

import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.freeze

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

//TODO Remove this when K/N coroutines better implements this
internal object Main : MainCoroutineDispatcher() {

    override val immediate: MainCoroutineDispatcher = this

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.freeze().run()
            } catch (err: Throwable) {
                println("UNCAUGHT ${err.message ?: ""}")
                println(err)
                throw err
            }
        }
    }
}
