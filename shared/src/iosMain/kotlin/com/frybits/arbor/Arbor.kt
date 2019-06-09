package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 *
 * iOS Arbor static class
 */

//Shared function calls

/**
 * Adds a branch to Arbor.
 *
 * @param branch Branch to add to Arbor for logging
 */
fun addBranch(branch: Branch) {
    sharedAddBranch(branch)
}

/**
 * Removes a branch from Arbor. Does nothing if Branch is not already in Arbor
 *
 * @param branch Branch to remove. Note, this must be an object that exists in Arbor already.
 */
fun removeBranch(branch: Branch) {
    sharedRemoveBranch(branch)
}

/**
 * Clears Arbor of all branches
 */
fun clear() {
    sharedClear()
}

/**
 * Sends a log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param level Log level for this log
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 * @param throwable Throwable for this log. Can be null
 */
fun log(level: Level, tag: String, message: String?, throwable: Throwable?) {
    sharedLog(level, tag, message, throwable)
}

//All other extras for iOS calls

//Log

/**
 * Sends a log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param level Log level for this log
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 */
fun log(level: Level, tag: String, message: String?) = log(level, tag, message, null)

/**
 * Sends a log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param level Log level for this log
 * @param tag Tag of this log
 * @param throwable Throwable for this log. Can be null
 */
fun log(level: Level, tag: String, throwable: Throwable?) = log(level, tag, null, throwable)

//Error

/**
 * Helper function to send an [Level.Error] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 */
fun e(tag: String, message: String?) = e(tag, message, null)

/**
 * Helper function to send an [Level.Error] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param throwable Throwable for this log. Can be null
 */
fun e(tag: String, throwable: Throwable?) = e(tag, null, throwable)

/**
 * Helper function to send an [Level.Error] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 * @param throwable Throwable for this log. Can be null
 */
fun e(tag: String, message: String?, throwable: Throwable?) = log(Level.Error, tag, message, throwable)

//Warn

/**
 * Helper function to send an [Level.Warn] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 */
fun w(tag: String, message: String?) = w(tag, message, null)

/**
 * Helper function to send an [Level.Warn] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param throwable Throwable for this log. Can be null
 */
fun w(tag: String, throwable: Throwable?) = w(tag, null, throwable)

/**
 * Helper function to send an [Level.Warn] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 * @param throwable Throwable for this log. Can be null
 */
fun w(tag: String, message: String?, throwable: Throwable?) = log(Level.Warn, tag, message, throwable)

//Info

/**
 * Helper function to send an [Level.Info] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 */
fun i(tag: String, message: String?) = i(tag, message, null)

/**
 * Helper function to send an [Level.Info] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param throwable Throwable for this log. Can be null
 */
fun i(tag: String, throwable: Throwable?) = i(tag, null, throwable)

/**
 * Helper function to send an [Level.Info] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 * @param throwable Throwable for this log. Can be null
 */
fun i(tag: String, message: String?, throwable: Throwable?) = log(Level.Info, tag, message, throwable)

//Debug

/**
 * Helper function to send an [Level.Debug] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 */
fun d(tag: String, message: String?) = d(tag, message, null)

/**
 * Helper function to send an [Level.Debug] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param throwable Throwable for this log. Can be null
 */
fun d(tag: String, throwable: Throwable?) = d(tag, null, throwable)

/**
 * Helper function to send an [Level.Debug] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 * @param throwable Throwable for this log. Can be null
 */
fun d(tag: String, message: String?, throwable: Throwable?) = log(Level.Debug, tag, message, throwable)

//Verbose

/**
 * Helper function to send an [Level.Verbose] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 */
fun v(tag: String, message: String?) = v(tag, message, null)

/**
 * Helper function to send an [Level.Verbose] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param throwable Throwable for this log. Can be null
 */
fun v(tag: String, throwable: Throwable?) = v(tag, null, throwable)

/**
 * Helper function to send an [Level.Verbose] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
 *
 * @param tag Tag of this log
 * @param message Message for this log. Can be null
 * @param throwable Throwable for this log. Can be null
 */
fun v(tag: String, message: String?, throwable: Throwable?) = log(Level.Verbose, tag, message, throwable)
