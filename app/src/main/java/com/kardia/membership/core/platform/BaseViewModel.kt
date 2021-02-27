
package com.kardia.membership.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kardia.membership.core.exception.Failure

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    var failureData: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure?) {
        this.failureData.value = failure
    }
}