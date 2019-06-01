package com.frybits.arbor

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal expect object ArborDispatchers {

    val Logger: CoroutineDispatcher
}
