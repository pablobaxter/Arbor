package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 *
 * Generic abstract branch. This is where setup and teardown occur when adding or removing the branch.
 *
 * NOTE: Due to Coroutines not working well with K/N, the intermediate class [BranchDispatcher] is defined for each platform
 * to allow the correct multithreaded functionality in JVM, but the workaround with only main-thread functionality for iOS
 */

abstract class Branch(
        /**
         * The highest log level this branch should log to.
         */
        private val logLevel: Level,

        /**
         * The list of tags to filter on. An empty list means no filter
         */
        filterTags: List<String> = listOf()
) {

    private val filterTagSet = filterTags.toSet()
    private val branchDispatcher = BranchDispatcher(this)

    internal fun sendLog(log: Log) {
        branchDispatcher.submit(log)
    }

    internal fun notifyAdd() {
        branchDispatcher.submit(Add(this))
    }

    internal fun notifyRemove() {
        branchDispatcher.submit(Remove(this))
    }

    internal fun log(log: Log) {
        if (log.level >= logLevel && (filterTagSet.isEmpty() || filterTagSet.contains(log.tag))) {
            onLog(log.level, log.tag, log.message, log.throwable)
        }
    }

    /**
     * Called when the branch is added to Arbor. [onLog] is guaranteed not to be called until this method is completed
     *
     * This is called on the main thread.
     */
    abstract fun onAdd()

    /**
     * Called when the branch is removed from Arbor. [onLog] will not be called while in this method. However, this method
     * might not be called until all logs currently awaiting in the queue are completed.
     *
     * This is called on the main thread.
     */
    abstract fun onRemove()

    /**
     * Called whenever Arbor.log() or any of the variants are called, and if allowed by the branch filters.
     * For example if this branch was passed in [Level.Info], only logs with [level] [Level.Info] and lower will be passed
     * to this function
     *
     * This is called on a background thread owned by the branch.
     *
     * NOTE: On iOS, this is called on the main thread. Will changed when Coroutines for K/N is fixed to allow multithreading.
     */
    abstract fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?)
}
