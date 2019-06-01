package com.frybits.arbor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal actual object ArborDispatchers {

    @JvmSynthetic
    internal actual val Logger: CoroutineDispatcher = Dispatchers.IO

}
