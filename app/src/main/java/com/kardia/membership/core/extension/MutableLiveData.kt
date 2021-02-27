package com.kardia.membership.core.extension

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}