package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */

fun <T> MutableCollection<T>.removeForEach(unit: (T) -> Unit) {
    val iterator = iterator()
    while (iterator.hasNext()) {
        val it = iterator.next()
        iterator.remove()
        unit(it)
    }
}
