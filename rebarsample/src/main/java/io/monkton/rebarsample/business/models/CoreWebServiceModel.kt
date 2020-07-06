package io.monkton.rebarsample.business.models

import android.os.AsyncTask
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.monkton.rebar.network.WebServiceRequest
import io.monkton.rebar.network.WebServiceResult

public typealias WebServicePerform = () -> WebServiceResult

abstract class CoreWebServiceModel : ViewModel(), LifecycleObserver {

    /**
     * @suppress
     */
    var httpWrapper: WebServiceRequest? = null

    /**
     * Creates the wrapper class
     */
    abstract fun instantiateRequest()

    /**
     * Clears out the view model
     */
    override fun onCleared() {
        this.httpWrapper?.cancel()
        this.httpWrapper = null
    }

    fun execute(toExecute: WebServicePerform, response: ((WebServiceResult) -> (Unit))) {

        // Perform the app login, in the background
        AsyncTask.THREAD_POOL_EXECUTOR.execute {

            // Create the http wrapper for the request
            this.instantiateRequest()

            // Perform the login
            var result = toExecute()

            // Cleanup
            this.httpWrapper = null

            // Hits the result
            response(result)

        }

    }

}