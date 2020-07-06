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

import android.database.Cursor
import io.monkton.rebar.crypto.RebarCoreCrypto
import io.monkton.rebar.database.Database
import io.monkton.rebar.database.DatabaseValues
import io.monkton.rebar.ui.RebarApplication
import io.monkton.rebarsample.business.database.SampleAppDatabase
import org.json.JSONArray
import org.json.JSONObject

class GenericItemManager private constructor() {

    /**
     * Retrieves the database to be used for reading and writing data
     */
    private val database: Database get() = SampleAppDatabase.databaseInstance

    /**
     * Retrieves the number of items present in the table
     */
    fun numberOfItems() : Int {
        return this.database.scalarInt("SELECT COUNT(*) AS COUNTED FROM APP_ITEM", "COUNTED")
    }

    /**
     * Sync items in the database
     */
    fun sync(data: JSONObject) {

        // Safety first!
        if (!data.has("items")) {
            return
        }

        // Grab data to sync
        val toSync = data["items"] as JSONArray
        var items = mutableListOf<GenericItem>()

        // Parse the items
        for (x in 0..toSync.length()) {
            val asDictionary = toSync[x] as JSONObject
            items.add(GenericItem.fromJson(asDictionary))
        }

        // Iterate the items
        items.forEach {
            it.itemId = if (it.itemRefId == null) -1 else getItemId(it.itemRefId!!)

            if (it.itemId > 0 && it.deleted)
                deleteItem(it.itemRefId!!)
            else if (it.itemId > 0 && !it.deleted)
                updateItem(it)
            else if (it.itemId < 0)
                addItem(it)
        }
    }

    /**
     * Sync items in the database
     */
    fun sync(items: List<GenericItem>) {
        items.forEach {
            it.itemId = if (it.itemRefId == null) -1 else getItemId(it.itemRefId!!)

            if (it.itemId > 0 && it.deleted)
                deleteItem(it.itemRefId!!)
            else if (it.itemId > 0 && !it.deleted)
                updateItem(it)
            else if (it.itemId < 0)
                addItem(it)
        }
    }

    /**
     * Retrieves a list of items from the database
     */
    fun getItem(itemRefId: String): GenericItem? {

        val itemId = getItemId(itemRefId)

        if (itemId <= 0)
            return null;

        var foundItem: GenericItem? = null

        // Run the asset cursor
        val assetCursor = this.database.rawQuery("SELECT * FROM APP_ITEM WHERE ITEM_ID = $itemId", null)

        // While we have data available in our cursor
        while (assetCursor.moveToNext()) {
            foundItem = read(assetCursor)!!
        } // while

        // Done with the cursor
        assetCursor.close()

        return foundItem
    }

    /**
     * Retrieves a list of items from the database
     */
    fun getItems(): List<GenericItem> {

        var items = mutableListOf<GenericItem>()

        // Run the asset cursor
        val assetCursor = this.database.rawQuery("SELECT * FROM APP_ITEM", null)

        // While we have data available in our cursor
        while (assetCursor.moveToNext()) {
            items.add(read(assetCursor)!!)
        } // while

        // Done with the cursor
        assetCursor.close()

        return items
    }

    /**
     * Indicates if the item exists in the database
     */
    fun exists(item: GenericItem) : Boolean {
        return getItemId(item.itemRefId!!) > 0
    }

    /**
     * Retrieves the items database id for the specific reference identifier
     */
    fun getItemId(referenceId: String) : Int {
        return this.database.scalarInt("SELECT ITEM_ID FROM APP_ITEM WHERE ITEM_REF_ID = '$referenceId'", "ITEM_ID")
    }

    /**
     * Updates an item within the database
     */
    fun updateItem(item: GenericItem) {
        val itemJson = item.asJson

        val vals = DatabaseValues()
        vals.putInt("ITEM_ID", item.itemId)
        vals.putString("ITEM_JSON", itemJson)

        // Update the value
        this.database.update("APP_ITEM", vals, "ITEM_ID=?", arrayOf(item.itemId.toString()))

    }

    /**
     * Adds a new item to the database
     */
    fun addItem(item: GenericItem) {

        // Set the item id it doesn't exist
        if (item.itemRefId == null) {
            item.itemRefId = "item_" + RebarCoreCrypto.instance.randomAsString(16)
        }

        val itemJson = item.asJson

        val vals = DatabaseValues()
        vals.putString("ITEM_REF_ID", item.itemRefId)
        vals.putString("ITEM_JSON", itemJson)

        // Insert the value
        this.database.insert("APP_ITEM", vals, null)

    }

    /**
     * Removes an item from the database
     */
    fun deleteItem(itemRefId: String) {

        val itemId = getItemId(itemRefId)

        if (itemId <= 0)
            return

        this.database.delete("APP_ITEM", "ITEM_ID=?", arrayOf(itemId.toString()))

    }

    /**
     * Reads an item from the database cursor
     */
    fun read(reader: Cursor): GenericItem? {
        val generatedItem = GenericItem.fromJson(Database.readString(reader, "ITEM_JSON"))

        generatedItem.itemId = Database.readInt(reader, "ITEM_ID")
        generatedItem.itemRefId = Database.readString(reader, "ITEM_REF_ID")

        return generatedItem
    }


    companion object {

        /**
         * The singleton instance of the item manager
         */
        val instance: GenericItemManager = GenericItemManager()

    }
}