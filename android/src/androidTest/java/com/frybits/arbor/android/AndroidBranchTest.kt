package com.frybits.arbor.android

import android.os.Looper
import androidx.test.runner.AndroidJUnit4
import com.frybits.arbor.Arbor
import com.frybits.arbor.Branch
import com.frybits.arbor.Level
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.`is` as isEqualTo

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

@RunWith(AndroidJUnit4::class)
class AndroidBranchTest {

    @Before
    fun setup() {
        Arbor.clear()
    }

    @Test(timeout = 1000)
    fun AddBranchIsMainLooper() {
        val completableDeferred = CompletableDeferred<Boolean>()
        Arbor.addBranch(object : Branch(Level.Info) {
            override fun onAdd() {
                Assert.assertThat(Looper.myLooper(), isEqualTo(Looper.getMainLooper()))
                completableDeferred.complete(true)
            }

            override fun onRemove() {
                if (!completableDeferred.isCompleted) {
                    fail("onRemove called before test finish")
                }
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                fail("onLog was called!")
            }
        })
        runBlocking {
            completableDeferred.await()
        }
    }

    @Test(timeout = 1000)
    fun RemoveBranchIsMainLooper() {
        val completableDeferred = CompletableDeferred<Boolean>()
        val branch = object : Branch(Level.Info) {
            override fun onAdd() {
                if (completableDeferred.isCompleted) {
                    fail("onAdd message was added more than once!")
                }
            }

            override fun onRemove() {
                Assert.assertThat(Looper.myLooper(), isEqualTo(Looper.getMainLooper()))
                completableDeferred.complete(true)
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                fail("onLog was called")
            }
        }
        Arbor.addBranch(branch)
        Arbor.removeBranch(branch)
        runBlocking {
            completableDeferred.await()
        }
    }

    @Test(timeout = 1000)
    fun LogIsNotMainLooper() {
        val completableDeferred = CompletableDeferred<Boolean>()
        Arbor.addBranch(object : Branch(Level.Info) {
            override fun onAdd() {
                if (completableDeferred.isCompleted) {
                    fail("onAdd message was added more than once!")
                }
            }

            override fun onRemove() {
                if (!completableDeferred.isCompleted) {
                    fail("onRemove was called")
                }
            }

            override fun onLog(level: Level, tag: String, message: String?, throwable: Throwable?) {
                Assert.assertThat(Looper.myLooper(), not(Looper.getMainLooper()))
                completableDeferred.complete(true)
            }
        })
        Arbor.w("Blah", "This is a test")
        runBlocking {
            completableDeferred.await()
        }
    }
}
