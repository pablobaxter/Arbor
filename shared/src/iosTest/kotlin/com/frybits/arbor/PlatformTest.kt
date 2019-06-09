package com.frybits.arbor

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

class PlatformTest {

    init {
        testEnvironment = true
    }

    @Test
    fun `Check for correct variant`() {
        assertEquals("ios", VARIANT, "Test is in wrong variant")
    }
}
