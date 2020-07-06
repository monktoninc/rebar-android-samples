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

package io.monkton.rebarsample.business.database

import io.monkton.rebar.database.Database
import io.monkton.rebar.database.DatabaseManager

// Declare an app name for this database
const val SAMPLE_APP_DATABASE_NAME: String = "SAMPLE_APP_DATABASE"

/**
 * Created by harold on 3/4/18.
 */
public class SampleAppDatabase : Database() {

    override val databaseName: String
        get() {
            return "sample-app.sqlite"
        };

    /**
     * Enforces upgrades to the local database
     */
    override fun verfiyUpgrades() {
        super.verfiyUpgrades();

        // Grab the current database version
        var currentVersion = getDatabaseVersion(SAMPLE_APP_DATABASE_NAME);

        // If this version is <= 1
        if (currentVersion <= 1) {

            // Create a new database table
            this.executeUpdate("CREATE TABLE APP_ITEM ( ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT , ITEM_REF_ID TEXT  , ITEM_JSON TEXT )")

            // update the current app version
            currentVersion = 2

            // Update the current version
            setDatabaseVersion(currentVersion, SAMPLE_APP_DATABASE_NAME)

        }

    }

    companion object {
        val databaseInstance: Database
            get() = DatabaseManager.instance.get(SampleAppDatabase::class.javaObjectType)!!
    }

}