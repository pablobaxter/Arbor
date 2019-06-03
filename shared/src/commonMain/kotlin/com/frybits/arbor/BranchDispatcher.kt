package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

internal expect class BranchDispatcher(branch: Branch) {

    fun submit(action: Action)
}
