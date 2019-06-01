package com.frybits.arbor

import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

class LevelTest {

    @Test
    fun `Checking if Error is most restrictive`() {
        assertTrue("Error is not more restrictive than Warn") {
            Error >= Warn
        }
        assertTrue("Error is not more restrictive than Info") {
            Error >= Info
        }
        assertTrue("Error is not more restrictive than Debug") {
            Error >= Debug
        }
        assertTrue("Error is not more restrictive than Verbose") {
            Error >= Verbose
        }
        assertTrue("Error does not equal itself") {
            Error == Error
        }
    }

    @Test
    fun `Checking if Verbose is least restrictive`() {
        assertTrue("Verbose is more restrictive then  Error") {
            Verbose <= Error
        }
        assertTrue("Verbose is more restrictive then Warn") {
            Verbose <= Warn
        }
        assertTrue("Verbose is more restrictive then Info") {
            Verbose <= Info
        }
        assertTrue("Verbose is more restrictive then Debug") {
            Verbose <= Debug
        }
        assertTrue("Verbose does not equal itself") {
            Verbose == Verbose
        }
    }
}
