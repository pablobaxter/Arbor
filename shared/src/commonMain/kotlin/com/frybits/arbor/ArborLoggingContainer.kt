package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */


//This is a mediator so that each platform can handle dispatching the Arbor Actions to an appropriate thread
//This may change once K/N handles multithreading using Coroutines betters
internal expect class ArborLoggingContainer() {

    internal fun submit(action: Action)

    internal fun branchCount(): Int
}
