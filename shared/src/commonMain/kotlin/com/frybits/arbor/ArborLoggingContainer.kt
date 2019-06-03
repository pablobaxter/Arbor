package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal expect class ArborLoggingContainer() {

    internal fun submit(action: Action)

    internal fun branchCount(): Int
}
