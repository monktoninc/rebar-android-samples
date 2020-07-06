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

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

/**
 * An item we can pass around
 */
@Parcelize
class GenericItem(var isNew: Boolean = false, var itemId: Int = -1, var deleted: Boolean = false, var itemRefId: String? = null, var name: String? = null) : Parcelable {


    /**
     * Converts the object to a JSON String
     */
    val asJson: String
        get() {
            return asJsonObject.toString()
        }

    /**
     * Converts the object to a JSONObject
     */
    val asJsonObject: JSONObject
        get() {
            val json = JSONObject()

            json.put("name", if (name == null) "" else name )
            json.put("itemRefId", if (itemRefId == null) "" else itemRefId )
            json.put("deleted", deleted)

            return json
        }


    companion object {

        /**
         * Loads the item from JSON string
         */
        fun fromJson(json: String): GenericItem {
            return fromJson(JSONObject(json))
        }

        /**
         * Loads the item from JSON string
         */
        fun fromJson(json: JSONObject): GenericItem {

            val item = GenericItem()

            item.name = json.getString("name")
            item.itemRefId = json.getString("itemRefId")

            if (json.has("deleted"))
                item.deleted = json.getBoolean("deleted");

            return item;
        }

    }

}