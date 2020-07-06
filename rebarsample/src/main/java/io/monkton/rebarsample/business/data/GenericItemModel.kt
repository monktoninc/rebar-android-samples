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

package io.monkton.rebarsample.business.data


import androidx.lifecycle.ViewModel
import android.os.AsyncTask

class GenericItemModel : ViewModel() {

    /**
     * Handles message inbox data
     */
    var requestResponse: GenericItemLiveData = GenericItemLiveData()

    /**
     * Loads current threads from the database for presentation
     */
    fun loadCachedItems() {

        // Perform the app login, in the background
        AsyncTask.THREAD_POOL_EXECUTOR.execute {

            // Grabs all of the cached messages we will want to display
            var items = GenericItemManager.instance.getItems()

            // Post them to our handler
            requestResponse.postValue(items)

        }

    }



}