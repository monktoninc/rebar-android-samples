package io.monkton.rebarsample.business.models

import io.monkton.rebar.network.WebServiceRequest
import io.monkton.rebar.network.WebServiceResult
import io.monkton.rebarsample.business.data.GenericItemLiveData
import io.monkton.rebarsample.business.data.WebServiceLiveData
import io.monkton.rebarsample.ui.AppUtil

open class SampleWebServiceModel : CoreWebServiceModel() {

    /**
     * Setup the wrapper with our service name so we can sign the requests
     */
    override fun instantiateRequest() {
        this.httpWrapper = WebServiceRequest(AppUtil.instance.webServiceName)
    }

    /**
     * Handles message inbox data
     */
    var webServiceData: WebServiceLiveData = WebServiceLiveData()

    /**
     * Loads current threads from the database for presentation
     */
    fun get(responseRefId: String) {

        /**
         * How we will build out the request to the server
         */
        val requestServices =  {
            this.httpWrapper!!.get("https://${AppUtil.instance.webServiceHost}/demo/${responseRefId}")
        }

        this.execute(requestServices, {

            if (it.hadError || it.hadJsonError || !it.isJSON) {
                webServiceData.postValue(it)
                return@execute
            }

            val result = WebServiceResult()

//            // Grab the JSON value
//            val asJson = it.json!!
//            var questionnaireList = ArrayList<CoronaQuestionnaireResponse>()
//
//            if (asJson.has("questionnaireRefId")) {
//                questionnaireList.add(CoronaQuestionnaireResponse.fromJson(asJson))
//            }
//
            webServiceData.postValue(result)

        })

    }

    /**
     * Loads current threads from the database for presentation
     */
    fun getCurrentQuestionnaire() {


        /**
         * How we will build out the request to the server
         */
        val requestServices =  {
            this.httpWrapper!!.get("https://${AppUtil.instance.webServiceHost}/questionnaire")
        }

        this.execute(requestServices, {

            val result = WebServiceResult()

//            // Grab the JSON value
//            val asJson = it.json!!
//            var questionnaireList = ArrayList<CoronaQuestionnaireResponse>()
//
//            if (asJson.has("questionnaireRefId")) {
//                questionnaireList.add(CoronaQuestionnaireResponse.fromJson(asJson))
//            }
//
            webServiceData.postValue(result)
        })



    }

    /**
     * Loads current threads from the database for presentation
    fun create(response: CoronaQuestionnaireResponse) {


        /**
         * How we will build out the request to the server
         */
        val requestServices = {
            val postAsJson = response.asJson
            val post = response.asJson.toByteArray()
            this.httpWrapper!!.post("https://${CoronaUtil.instance.webServiceHost}/responses", "application/json", null, post)
        }

        this.execute(requestServices, {

            if (it.hadError || it.hadJsonError || !it.isJSON) {
                webServiceData.postValue(it)
                return@execute
            }

            // Grab the JSON value
            val asJson = it.json!!
            var questionnaireList = ArrayList<CoronaQuestionnaireResponse>()

            if (asJson.has("questionnaireRefId")) {
                questionnaireList.add(CoronaQuestionnaireResponse.fromJson(asJson))
            }

            responseData.postValue(questionnaireList)


        })

    }
     */

}