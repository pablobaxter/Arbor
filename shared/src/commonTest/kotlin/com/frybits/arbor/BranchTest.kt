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
        runTest {
            Arbor.clear()
            val onAddCompletable = CompletableDeferred<Boolean>()
            Arbor.addBranch(object : Branch(Verbose) {
                override fun onAdd() {
                    onAddCompletable.complete(true)
                }

                override fun onRemove() {}

                override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {}

            })

            withTimeout(1000) {
                onAddCompletable.await()
            }
        }
    }

    @Test
    fun `Remove branch from Arbor`() {
        runTest {
            Arbor.clear()
            val onRemoveCompletableDeferred = CompletableDeferred<Boolean>()
            val branch = object : Branch(Verbose) {
                override fun onAdd() {
                    println("Branch was added")
                }

                override fun onRemove() {
                    onRemoveCompletableDeferred.complete(true)
                }

                override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                }
            }
            Arbor.addBranch(branch)
            Arbor.removeBranch(branch)

            withTimeout(1000) {
                onRemoveCompletableDeferred.await()
            }
        }
    }

    @Test
    fun `Clear branches from Arbor`() {
        runTest {
            Arbor.clear()
            delay(100)
            assertEquals(0, Arbor.branchCount(), "Arbor is not initially clear!")
            val onDummy1Add = CompletableDeferred<Boolean>()
            val onDummy2Add = CompletableDeferred<Boolean>()

            val onDummy1Remove = CompletableDeferred<Boolean>()
            val onDummy2Remove = CompletableDeferred<Boolean>()

            val dummyBranch1 = object : Branch(Verbose) {
                override fun onAdd() {
                    onDummy1Add.complete(true)
                }

                override fun onRemove() {
                    onDummy1Remove.complete(true)
                }

                override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {}
            }
            val dummyBranch2 = object : Branch(Verbose) {
                override fun onAdd() {
                    onDummy2Add.complete(true)
                }

                override fun onRemove() {
                    onDummy2Remove.complete(true)
                }

                override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {}
            }

            Arbor.addBranch(dummyBranch1)
            withTimeout(100) {
                onDummy1Add.await()
                assertEquals(1, Arbor.branchCount(), "Arbor branch count is off!")
            }

            Arbor.addBranch(dummyBranch2)
            withTimeout(100) {
                onDummy2Add.await()
                assertEquals(2, Arbor.branchCount(), "Arbor branch count is off!")
            }

            Arbor.clear()
            withTimeout(1000) {
                onDummy1Remove.await()
                onDummy2Remove.await()
                assertEquals(0, Arbor.branchCount(), "Arbor branch count is off!")
            }
        }
    }

    @Test
    fun `No duplicate branches test`() {
        runTest {
            Arbor.clear()
            val onDummy1Add = CompletableDeferred<Boolean>()
            val onDummy2Add = CompletableDeferred<Boolean>()

            val onDummy1Remove = CompletableDeferred<Boolean>()
            val onDummy2Remove = CompletableDeferred<Boolean>()

            val dummyBranch1 = object : Branch(Verbose) {
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

            Arbor.addBranch(dummyBranch1)
            withTimeout(100) {
                onDummy1Add.await()
                assertEquals(1, Arbor.branchCount(), "Arbor branch count is off!")
            }

            Arbor.addBranch(dummyBranch1)
            assertFailsWith(TimeoutCancellationException::class, "Received duplicate branch!") {
                runTest {
                    withTimeout(100) {
                        onDummy2Add.await()
                    }
                }
            }

            Arbor.removeBranch(dummyBranch1)
            withTimeout(100) {
                onDummy1Remove.await()
                assertEquals(0, Arbor.branchCount(), "Arbor branch count is off!")
            }

            Arbor.removeBranch(dummyBranch1)
            assertFailsWith(TimeoutCancellationException::class, "Branch was removed twice!") {
                runTest {
                    withTimeout(100) {
                        onDummy2Remove.await()
                    }
                }
            }
        }
    }

    @Test
    fun `Ensure logs are received`() {
        fun createString(level: Level, tag: String, message: String?, throwable: Throwable?): String {
            return "level $level, tag $tag, message $message, throwable: $throwable"
        }

        runTest {
            Arbor.clear()
            val onLogString = CompletableDeferred<String>()
            Arbor.addBranch(object : Branch(Error) {
                override fun onAdd() {
                }

                override fun onRemove() {
                }

                override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                    onLogString.complete(createString(level, tag, message, throwable))
                }
            })

            Arbor.e("Foo", "Some message", Exception("blah"))

            withTimeout(100) {
                val string = onLogString.await()
                assertEquals(createString(Error, "Foo", "Some message", Exception("blah")), string, "Strings are mismatched!")
            }
        }
    }

    @Test
    fun `Ensure log levels are filtered`() {
        runTest {
            Arbor.clear()
            val onErrorLevelLog = CompletableDeferred<Level>()
            val onWarnLevelLog = CompletableDeferred<Level>()
            val onInfoLevelLog = CompletableDeferred<Level>()
            val onDebugLevelLog = CompletableDeferred<Level>()
            val onVerboseLevelLog = CompletableDeferred<Level>()

            Arbor.addBranch(object : Branch(Info) {
                override fun onAdd() {
                }

                override fun onRemove() {
                }

                override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                    when (level) {
                        Error -> onErrorLevelLog.complete(level)
                        Warn -> onWarnLevelLog.complete(level)
                        Info -> onInfoLevelLog.complete(level)
                        Debug -> onDebugLevelLog.complete(level)
                        Verbose -> onVerboseLevelLog.complete(level)
                    }
                }
            })

            Arbor.i("Blah")
            Arbor.e("Foo")
            Arbor.w("Bar")
            Arbor.d("Debugging stuff!")

            withTimeout(100) {
                val info = onInfoLevelLog.await()
                assertEquals(Info, info, "Received wrong level")

                val error = onErrorLevelLog.await()
                assertEquals(Error, error, "Received wrong level")

                val warn = onWarnLevelLog.await()
                assertEquals(Warn, warn, "Received wrong level")
            }

            assertFailsWith(TimeoutCancellationException::class, "Received debug log when it should be filtered!") {
                runTest {
                    withTimeout(100) {
                        onDebugLevelLog.await()
                    }
                }
            }
        }
    }

    @Test
    fun `Ensure log tags are filtered`() {
        runTest {
            Arbor.clear()
            val tag1 = "Foo"
            val tag2 = "Bar"
            val tag3 = "Baz"
            val onTag1Log = CompletableDeferred<String>()
            val onTag2Log = CompletableDeferred<String>()
            val onTag3Log = CompletableDeferred<String>()

            Arbor.addBranch(object : Branch(Verbose, tag1, tag2) {
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

            Arbor.i(tag1)
            Arbor.e(tag2)
            Arbor.w(tag3)

            withTimeout(100) {
                val tag1Result = onTag1Log.await()
                assertEquals(tag1, tag1Result, "Received wrong tag")

                val tag2Result = onTag2Log.await()
                assertEquals(tag2, tag2Result, "Received wrong tag")
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
}
