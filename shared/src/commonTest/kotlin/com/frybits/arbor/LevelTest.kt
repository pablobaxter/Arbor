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
    fun `Checking if Warn is above Error and below others`() {
        assertTrue("Warn is more restrictive then  Error") {
            Level.Warn <= Level.Error
        }
        assertTrue("Warn does not equal itself") {
            Level.Warn == Level.Warn
        }
        assertTrue("Verbose is not more restrictive then Info") {
            Level.Warn >= Level.Info
        }
        assertTrue("Verbose is not more restrictive then Debug") {
            Level.Warn >= Level.Debug
        }
        assertTrue("Verbose is not more restrictive then Verbose") {
            Level.Warn >= Level.Verbose
        }
    }

    @Test
    fun `Checking if Info is above Warn and below others`() {
        assertTrue("Info is more restrictive then  Error") {
            Level.Info <= Level.Error
        }
        assertTrue("Info is more restrictive then  Warn") {
            Level.Info <= Level.Warn
        }
        assertTrue("Info does not equal itself") {
            Level.Info == Level.Info
        }
        assertTrue("Info is not more restrictive then Debug") {
            Level.Info >= Level.Debug
        }
        assertTrue("Info is not more restrictive then Verbose") {
            Level.Info >= Level.Verbose
        }
    }

    @Test
    fun `Checking if Debug is above Info and below others`() {
        assertTrue("Debug is more restrictive then  Error") {
            Level.Debug <= Level.Error
        }
        assertTrue("Debug is more restrictive then  Warn") {
            Level.Debug <= Level.Warn
        }
        assertTrue("Debug is more restrictive then  Info") {
            Level.Debug <= Level.Info
        }
        assertTrue("Debug does not equal itself") {
            Level.Debug == Level.Debug
        }
        assertTrue("Debug is not more restrictive then Verbose") {
            Level.Debug >= Level.Verbose
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
