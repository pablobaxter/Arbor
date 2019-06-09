package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

//Internal class to handle passing of actions to the branch, so that they can handle onAdd, onRemove, and onLog appropriately
internal expect class BranchDispatcher(branch: Branch) {

    fun submit(action: Action)
}
