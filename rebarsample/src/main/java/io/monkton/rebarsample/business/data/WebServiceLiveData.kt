package io.monkton.rebarsample.business.data

import androidx.lifecycle.MutableLiveData
import io.monkton.rebar.network.WebServiceResult

class WebServiceLiveData() : MutableLiveData<WebServiceResult>() {

    var data: List<WebServiceResult>? = null

    constructor(data: List<WebServiceResult>): this() {
        this.data = data
    }
}