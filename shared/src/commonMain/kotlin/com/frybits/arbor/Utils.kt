package com.frybits.arbor

/**
 * Frybits
 * Created by Pablo Baxter (Github: pablobaxter)
 */


/**
 * Iterates through the collection, passing the item into the lambda, then removing the item from the collection
 * Helpful is you need to perform some sort of action (such as a teardown) before removing the item from the list
 */
fun <T> MutableCollection<T>.forEachThenRemove(unit: (T) -> Unit) {
    val iterator = iterator()
    while (iterator.hasNext()) {
        val it = iterator.next()
        iterator.remove()
        unit(it)
    }
}
