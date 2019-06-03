package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

//TODO Re-do this class once Kotlin/Native better handles coroutines
internal actual class BranchDispatcher actual constructor(private val branch: Branch) {

    private var isPlanted = false

    actual fun submit(action: Action) {
        when (action) {
            is Add -> {
                branch.onAdd()
                isPlanted = true
            }

            is Remove -> {
                isPlanted = false
                branch.onRemove()
            }

            is Log -> {
                if (isPlanted) {
                    branch.log(action)
                }
            }

        }
    }
}
