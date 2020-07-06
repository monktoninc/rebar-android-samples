package io.monkton.rebarsample.ui

import io.monkton.rebar.ui.AppController

/**
 * Provides some generic tools that the app can use
 */
class AppUtil private constructor(){

    /**
     * A name to register the bearer tokens with
     */
    val webServiceName: String
        get() {
            return "sample-app"
        }

    /**
     * The host name of our web services we will invoke. You must define a "app.webservice.api" key
     * in the Rebar Hub Security Configuration section of your app
     */
    val webServiceHost: String
        get() {
            return AppController.instance.config!!.value("app.webservice.api")!!
        }

    companion object {

        /**
         * The singleton instance of this utility class
         */
        public val instance: AppUtil = AppUtil()

    }

}