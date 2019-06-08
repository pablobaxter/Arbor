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
            Level.Error >= Level.Warn
        }
        assertTrue("Error is not more restrictive than Info") {
            Level.Error >= Level.Info
        }
        assertTrue("Error is not more restrictive than Debug") {
            Level.Error >= Level.Debug
        }
        assertTrue("Error is not more restrictive than Verbose") {
            Level.Error >= Level.Verbose
        }
        assertTrue("Error does not equal itself") {
            Level.Error == Level.Error
        }
    }

    @Test
    fun `Checking if Verbose is least restrictive`() {
        assertTrue("Verbose is more restrictive then  Error") {
            Level.Verbose <= Level.Error
        }
        assertTrue("Verbose is more restrictive then Warn") {
            Level.Verbose <= Level.Warn
        }
        assertTrue("Verbose is more restrictive then Info") {
            Level.Verbose <= Level.Info
        }
        assertTrue("Verbose is more restrictive then Debug") {
            Level.Verbose <= Level.Debug
        }
        assertTrue("Verbose does not equal itself") {
            Level.Verbose == Level.Verbose
        }
    }
}
