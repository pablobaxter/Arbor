package com.frybits.arbor

import platform.UIKit.UIDevice

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

@Suppress("MayBeConstant")
actual val VARIANT = UIDevice.currentDevice.systemName

//Helper variable to allowing testing of iOS version of Arbor
internal var testEnvironment = false
