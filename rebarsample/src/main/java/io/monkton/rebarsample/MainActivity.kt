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

import android.os.Bundle

import io.monkton.rebarsample.business.models.SyncModel
import androidx.lifecycle.Observer
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import io.monkton.rebar.models.JsonDataResult
import io.monkton.rebar.ui.AppController
import io.monkton.rebar.ui.AuthenticatedActivity
import io.monkton.rebarsample.business.data.GenericItem
import io.monkton.rebarsample.business.data.GenericItemManager
import io.monkton.rebarsample.business.data.GenericItemModel
import io.monkton.rebarsample.ui.GenericItemEditorActivity
import io.monkton.rebarsample.ui.GenericItemRecyclerViewAdapter

/**
 * Defines the main activity for the project. This class will be the main entry point into the app
 * it is the screen that the user will see after authenticating, logging in.
 */
class MainActivity : AuthenticatedActivity() {

    // Our model for performing sync activities
    private var appModel: SyncModel? = null
    private var genericItemModel: GenericItemModel? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: GenericItemRecyclerViewAdapter
    private lateinit var viewManager: LinearLayoutManager

//    /**
//     * The button that will perform our syncing action
//     */
//    private val syncButton: Button
//        get() { return this.findViewById<Button>(R.id.rebar_sync) as Button }

    /**
     * Configures the view and lifecycle. We use models to perform background activities.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure the view
        setContentView(R.layout.activity_main)

        // Grab the reference to the app model we will use in this activity
        this.appModel = ViewModelProvider(this).get(SyncModel::class.java)

        // Listen for the current response to change from our HTTP request
        this.appModel!!.requestResponse.observe(this, Observer {
            data ->
            this.finishLoading(data)
        })

        // Grab the reference to the app model we will use in this activity
        this.genericItemModel = ViewModelProvider(this).get(GenericItemModel::class.java)

        // Listen for the current response to change from our HTTP request
        this.genericItemModel!!.requestResponse.observe(this, Observer {
            data ->
            this.reloadDataFromCollection(data)
        })

        viewManager = LinearLayoutManager(this)
        viewAdapter = GenericItemRecyclerViewAdapter { x -> viewItem(x) }

        recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        // Load the items from the database
        reloadFromDatabase();

    }

    /**
     * Displays the selected item
     */
    fun viewItem(item: GenericItem?) {
        if (item == null) {
            return
        }

        val intent = Intent(this, GenericItemEditorActivity::class.java)
        intent.putExtra(GenericItemEditorActivity.ITEM_IDENTIFIER, item.itemRefId)
        this.startActivity(intent)

    }

    fun addItem() {
        val intent = Intent(this, GenericItemEditorActivity::class.java)
        this.startActivity(intent)
    }

    /**
     * Logs the user off the app
     */
    private fun logoff() {
        AppController.instance.logoff()
    }

    /**
     * Performs the syncing operation
     */
    fun sync() {
        this.appModel!!.sync()
    }

    /**
     * Grabs the items from the database to render
     */
    fun reloadFromDatabase() {
        this.genericItemModel!!.loadCachedItems()
    }

    /**
     * Loads the items into the view adapter
     */
    private fun reloadDataFromCollection(items: List<GenericItem>?) {
        this.runOnUiThread {
            viewAdapter.replace(items)
        }
    }

    /**
     * Handles the callback when a sync is performed
     */
    @SuppressWarnings("unused")
    private fun finishLoading(result: JsonDataResult?) {

        // Can we process this request?
        if (result!!.wasSuccess && !result.hadError()) {

//            // Parse the items
//            val parsedItems = GenericItemParser.instance.parseItems(result.getDataAsMap()!!)
//
//            // Sync the items to the database
//            GenericItemManager.instance.sync(result.)
//
//            // Load the items from the database
//            reloadFromDatabase();

        }

    }

    /**
     * A option has been tapped
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.main_menu_logoff -> {
                logoff()
                true
            }
            R.id.main_menu_refresh -> {
                sync()
                true
            }
            R.id.main_menu_add -> {
                addItem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Create the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
