package io.monkton.rebarsample.business.models

import io.monkton.rebar.network.WebServiceRequest
import io.monkton.rebar.util.RebarUtil
import io.monkton.rebarsample.business.data.WebServiceLiveData
import io.monkton.rebarsample.ui.AppUtil

class WebServiceTokenModel  : CoreWebServiceModel() {

    override fun instantiateRequest() {
        this.httpWrapper = WebServiceRequest()
    }

    /**
     * Handles token response model
     */
    private var webServiceData: WebServiceLiveData = WebServiceLiveData()


    /**
     * Loads current threads from the database for presentation
     */
    fun generateApiTokens() {

        /**
         * How we will build out the request to the server
         */
        val requestServices =  {
            this.httpWrapper!!.get("https://${AppUtil.instance.webServiceHost}/api/v1/app/token")
        }

        this.execute(requestServices, {

            // Check to see if any errors occurred
            if (it.hadError || it.hadJsonError || !it.isJSON || it.json == null) {
                webServiceData.postValue(it)
                return@execute
            }

            // Grab the JSON value so we can use it later
            val asJson = it.json!!

            // Does the value have the auth key reference from our token service?
            if (asJson.has("authKeyRefId")) {
                // Grab the keys we will store
                val sharedKey = asJson.getString("authKeyRefId")
                val secretKey = asJson.getString("secretKey")

                // Store the web server bearer tokens for later
                RebarUtil.instance.setWebServiceApiKeys(AppUtil.instance.webServiceName, sharedKey, secretKey)
            }

            // This is done
            webServiceData.postValue(it)

        })

    }

}