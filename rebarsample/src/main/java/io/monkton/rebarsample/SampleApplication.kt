/**
 * MIT License
 * Copyright (c) 2018 Monkton, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.monkton.rebarsample

import io.monkton.rebar.database.DatabaseManager
import io.monkton.rebar.ui.AppController
import io.monkton.rebar.ui.RebarApplication

import io.monkton.rebarsample.business.SampleAppConfiguration
import io.monkton.rebarsample.business.database.SampleAppDatabase

/**
 * Created by harold on 3/4/18.
 */
class SampleApplication : RebarApplication() {

    /**
     * Performs the setup and configuration of the Rebar application
     */
    override fun setupConfiguration() {
        super.setupConfiguration()

        // Set the configuration for the app controller, this will customize app functionality
        AppController.instance.config = SampleAppConfiguration()

        // Register a custom database class
        DatabaseManager.instance.register(SampleAppDatabase::class.javaObjectType)

        // Configure the app for later usage
        AppController.instance.config!!.postInitialAuthenticationAction = {
            // Add in actions you'd like to perform AFTER the user has authenticated the
            // first time with the server and the databases are available. This gives you
            // full access to Rebar DAR protection and DIT protection
        }

        // Configure action after the user enters their passcode/biometric authentication
        AppController.instance.config!!.postPasscodeAuthenticationAction = {
            // Add in actions you'd like to perform AFTER the user has performed passcode authentication
            // With the app, or unlocked with biometrics.
        }


    }

}