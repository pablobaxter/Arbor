package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

object Arbor {

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

    /**
     * Helper function to send an [Level.Error] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
     *
     * @param tag Tag of this log
     * @param message Message for this log. Can be null
     * @param throwable Throwable for this log. Can be null
     */
    fun e(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Error, tag, message, throwable)

    /**
     * Helper function to send an [Level.Warn] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
     *
     * @param tag Tag of this log
     * @param message Message for this log. Can be null
     * @param throwable Throwable for this log. Can be null
     */
    fun w(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Warn, tag, message, throwable)

    /**
     * Helper function to send an [Level.Info] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
     *
     * @param tag Tag of this log
     * @param message Message for this log. Can be null
     * @param throwable Throwable for this log. Can be null
     */
    fun i(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Info, tag, message, throwable)

    /**
     * Helper function to send an [Level.Debug] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
     *
     * @param tag Tag of this log
     * @param message Message for this log. Can be null
     * @param throwable Throwable for this log. Can be null
     */
    fun d(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Debug, tag, message, throwable)

    /**
     * Helper function to send an [Level.Verbose] log to all [Branch] objects in Arbor. It's up to each branch to filter the log appropriately
     *
     * @param tag Tag of this log
     * @param message Message for this log. Can be null
     * @param throwable Throwable for this log. Can be null
     */
    fun v(tag: String, message: String? = null, throwable: Throwable? = null) = log(Level.Verbose, tag, message, throwable)
}
