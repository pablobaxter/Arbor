package com.frybits.arbor

import kotlinx.coroutines.runBlocking

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

actual fun runTest(unit: suspend () -> Unit) = runBlocking { unit.invoke() }
