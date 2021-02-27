package com.kardia.membership.core.extension

inline fun <T> Array<T>.mapInPlace(mutator: (T)->T) {
    this.forEachIndexed { idx, value ->
        mutator(value).let { newValue ->
            if (newValue !== value) this[idx] = mutator(value)
        }
    }
}

fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> {
    return map {
        if (block(it)) newValue else it
    }
}

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }