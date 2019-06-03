package com.frybits.arbor

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

class PlatformTest {

    @Test
    fun `Check for correct variant`() {
        assertEquals("jvm", Platform.VARIANT, "Test is in wrong variant")
    }
}
