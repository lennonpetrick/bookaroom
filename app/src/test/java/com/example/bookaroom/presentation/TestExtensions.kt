package com.example.bookaroom.presentation

import androidx.lifecycle.LiveData
import org.junit.jupiter.api.Assertions

fun <T : ViewState> LiveData<T>.observe(): List<T> {
    val states = mutableListOf<T>()
    this.observeForever { states.add(it) }
    return states
}

fun <T : ViewState> List<T>.assertValueAt(index: Int, predicate: (T) -> Boolean): List<T> {
    if (index >= size) throw AssertionError("Index out of bounds: $index >= $size")

    Assertions.assertTrue(predicate(this[index]))
    return this
}

fun <T : ViewState> List<T>.assertLastValue(predicate: (T) -> Boolean): List<T> {
    Assertions.assertTrue(predicate(this.last()))
    return this
}