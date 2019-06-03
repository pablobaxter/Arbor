package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

actual object Arbor {

    actual fun addBranch(branch: Branch) {
    }

    actual fun removeBranch(branch: Branch) {
    }

    actual fun clear() {
    }

    internal actual fun branchCount(): Int {
        return 0
    }

    actual fun log(level: Level, tag: String, message: String?, throwable: Throwable?) {
    }

    actual fun e(tag: String, message: String?, throwable: Throwable?) {
    }

    actual fun w(tag: String, message: String?, throwable: Throwable?) {
    }

    actual fun i(tag: String, message: String?, throwable: Throwable?) {
    }

    actual fun d(tag: String, message: String?, throwable: Throwable?) {
    }

    actual fun v(tag: String, message: String?, throwable: Throwable?) {
    }

}
