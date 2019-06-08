package com.frybits.arbor

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

abstract class BranchTest {

    @Test
    fun `Add branch to Arbor`() {
        clear()
        val onAddCompletable = CompletableDeferred<Boolean>()
        addBranch(object : Branch(Level.Verbose) {
            override fun onAdd() {
                onAddCompletable.complete(true)
            }

            override fun onRemove() {}

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {}

        })
        runTest {
            withTimeout(100) {
                onAddCompletable.await()
            }
        }
    }

    @Test
    fun `Remove branch from Arbor`() {
        clear()
        val onRemoveCompletableDeferred = CompletableDeferred<Boolean>()
        val branch = object : Branch(Level.Verbose) {
            override fun onAdd() {
                println("Branch was added")
            }

            override fun onRemove() {
                onRemoveCompletableDeferred.complete(true)
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
            }
        }
        addBranch(branch)
        removeBranch(branch)
        runTest {
            withTimeout(100) {
                onRemoveCompletableDeferred.await()
            }
        }
    }

    @Test
    fun `Clear branches from Arbor`() {
        clear()
        runTest {
            delay(100)
        }
        assertEquals(0, _branchCount(), "Arbor is not initially clear!")
        val onDummy1Add = CompletableDeferred<Boolean>()
        val onDummy2Add = CompletableDeferred<Boolean>()

        val onDummy1Remove = CompletableDeferred<Boolean>()
        val onDummy2Remove = CompletableDeferred<Boolean>()

        val dummyBranch1 = object : Branch(Level.Verbose) {
            override fun onAdd() {
                onDummy1Add.complete(true)
            }

            override fun onRemove() {
                onDummy1Remove.complete(true)
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {}
        }
        val dummyBranch2 = object : Branch(Level.Verbose) {
            override fun onAdd() {
                onDummy2Add.complete(true)
            }

            override fun onRemove() {
                onDummy2Remove.complete(true)
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {}
        }
        addBranch(dummyBranch1)
        runTest {
            withTimeout(100) {
                onDummy1Add.await()
                assertEquals(1, _branchCount(), "Arbor branch count is off!")
            }
        }
        addBranch(dummyBranch2)
        runTest {
            withTimeout(100) {
                onDummy2Add.await()
                assertEquals(2, _branchCount(), "Arbor branch count is off!")
            }

        }
        clear()
        runTest {
            withTimeout(100) {
                onDummy1Remove.await()
                onDummy2Remove.await()
                assertEquals(0, _branchCount(), "Arbor branch count is off!")
            }
        }
    }

    @Test
    fun `No duplicate branches test`() {
        clear()
        val onDummy1Add = CompletableDeferred<Boolean>()
        val onDummy2Add = CompletableDeferred<Boolean>()

        val onDummy1Remove = CompletableDeferred<Boolean>()
        val onDummy2Remove = CompletableDeferred<Boolean>()

        val dummyBranch1 = object : Branch(Level.Verbose) {
            override fun onAdd() {
                if (onDummy1Add.isCompleted) {
                    onDummy2Add.complete(true)
                } else {
                    onDummy1Add.complete(true)
                }
            }

            override fun onRemove() {
                if (onDummy1Remove.isCompleted) {
                    onDummy2Remove.complete(true)
                } else {
                    onDummy1Remove.complete(true)
                }
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {}
        }

        addBranch(dummyBranch1)
        runTest {
            withTimeout(100) {
                onDummy1Add.await()
                assertEquals(1, _branchCount(), "Arbor branch count is off!")
            }
        }
        addBranch(dummyBranch1)
        assertFailsWith(TimeoutCancellationException::class, "Received duplicate branch!") {
            runTest {
                withTimeout(100) {
                    onDummy2Add.await()
                }
            }
        }

        removeBranch(dummyBranch1)
        runTest {
            withTimeout(100) {
                onDummy1Remove.await()
                assertEquals(0, _branchCount(), "Arbor branch count is off!")
            }
        }
        removeBranch(dummyBranch1)
        assertFailsWith(TimeoutCancellationException::class, "Branch was removed twice!") {
            runTest {
                withTimeout(100) {
                    onDummy2Remove.await()
                }
            }
        }
    }


    @Test
    fun `Ensure logs are received`() {
        fun createString(level: Level, tag: String, message: String?, throwable: Throwable?): String {
            return "level $level, tag $tag, message $message, throwable: $throwable"
        }

        clear()
        val onLogString = CompletableDeferred<String>()
        addBranch(object : Branch(Level.Error) {
            override fun onAdd() {
            }

            override fun onRemove() {
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                onLogString.complete(createString(level, tag, message, throwable))
            }
        })
        e("Foo", "Some message", Exception("blah"))
        runTest {
            withTimeout(100) {
                val string = onLogString.await()
                assertEquals(createString(Level.Error, "Foo", "Some message", Exception("blah")), string, "Strings are mismatched!")
            }
        }
    }

    @Test
    fun `Ensure log levels are filtered`() {
        clear()
        val onErrorLevelLog = CompletableDeferred<Level>()
        val onWarnLevelLog = CompletableDeferred<Level>()
        val onInfoLevelLog = CompletableDeferred<Level>()
        val onDebugLevelLog = CompletableDeferred<Level>()
        val onVerboseLevelLog = CompletableDeferred<Level>()

        addBranch(object : Branch(Level.Info) {
            override fun onAdd() {
            }

            override fun onRemove() {
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                when (level) {
                    Level.Error -> onErrorLevelLog.complete(level)
                    Level.Warn -> onWarnLevelLog.complete(level)
                    Level.Info -> onInfoLevelLog.complete(level)
                    Level.Debug -> onDebugLevelLog.complete(level)
                    Level.Verbose -> onVerboseLevelLog.complete(level)
                }
            }
        })

        i("Blah")
        e("Foo")
        w("Bar")
        d("Debugging stuff!")
        runTest {
            withTimeout(100) {
                val info = onInfoLevelLog.await()
                assertEquals(Level.Info, info, "Received wrong level")

                val error = onErrorLevelLog.await()
                assertEquals(Level.Error, error, "Received wrong level")

                val warn = onWarnLevelLog.await()
                assertEquals(Level.Warn, warn, "Received wrong level")
            }
        }
        assertFailsWith(TimeoutCancellationException::class, "Received debug log when it should be filtered!") {
            runTest {
                withTimeout(100) {
                    onDebugLevelLog.await()
                }
            }
        }
    }

    @Test
    fun `Ensure log tags are filtered`() {
        clear()
        val tag1 = "Foo"
        val tag2 = "Bar"
        val tag3 = "Baz"
        val onTag1Log = CompletableDeferred<String>()
        val onTag2Log = CompletableDeferred<String>()
        val onTag3Log = CompletableDeferred<String>()

        addBranch(object : Branch(Level.Verbose, listOf(tag1, tag2)) {
            override fun onAdd() {
            }

            override fun onRemove() {
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                when (tag) {
                    tag1 -> onTag1Log.complete(tag)
                    tag2 -> onTag2Log.complete(tag)
                    tag3 -> onTag3Log.complete(tag)
                }
            }
        })

        i(tag1)
        e(tag2)
        w(tag3)
        runTest {
            withTimeout(100) {
                val tag1Result = onTag1Log.await()
                assertEquals(tag1, tag1Result, "Received wrong tag")

                val tag2Result = onTag2Log.await()
                assertEquals(tag2, tag2Result, "Received wrong tag")
            }
        }
        assertFailsWith(TimeoutCancellationException::class, "Received tag3 log when it should be filtered!") {
            runTest {
                withTimeout(100) {
                    onTag3Log.await()
                }
            }
        }
    }
}
