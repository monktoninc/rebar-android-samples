package io.monkton.rebarsample.ui

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import io.monkton.rebar.models.JsonDataResult
import io.monkton.rebar.ui.AuthenticatedActivity
import io.monkton.rebarsample.R
import io.monkton.rebarsample.business.data.GenericItem
import io.monkton.rebarsample.business.data.GenericItemManager
import io.monkton.rebarsample.business.models.SyncModel

class GenericItemEditorActivity : AuthenticatedActivity() {

    // Our model for performing sync activities
    private var appModel: SyncModel? = null

    /**
     * Text control to edit the name of the generic item
     */
    val genericItemName : EditText get() = this.findViewById(R.id.generic_edit_name_edit)

    /**
     * The item we are editing
     */
    var editItem: GenericItem? = null

    /**
     * Identifier for the thread in question
     */
    var genericItemIdentifier: String? = null

    /**
     * Save the INSTANCE state
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Write our data out
        this.flushDataToObject()

        // Add the parcable
        outState.putParcelable(ITEM_CACHED_IDENTIFIER, this.editItem!!)
    }

    /**
     * Configures the view and lifecycle. We use models to perform background activities.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure the view
        setContentView(R.layout.activity_generic_editor)

        // Grab the identifier for the thread...
        this.genericItemIdentifier = this.intent.getStringExtra(GenericItemEditorActivity.ITEM_IDENTIFIER)

        if (savedInstanceState != null && savedInstanceState.containsKey(ITEM_CACHED_IDENTIFIER)) {
            // Grab the item
            this.editItem = savedInstanceState.getParcelable<GenericItem>(ITEM_CACHED_IDENTIFIER)
        }
        else {
            if (this.genericItemIdentifier != null) {
                this.editItem = GenericItemManager.instance.getItem(this.genericItemIdentifier!!)
            }
            else {
                this.editItem = GenericItem()
                this.editItem!!.isNew = true
            }
        }

        // Sets the edit field name text box
        if (this.editItem!!.name != null) {
            this.genericItemName.setText(this.editItem!!.name!!)
        }

        // Grab the reference to the app model we will use in this activity
        this.appModel = ViewModelProvider(this).get(SyncModel::class.java)

        // Listen for the current response to change from our HTTP request
        this.appModel!!.requestResponse.observe(this, Observer {
            data ->
            this.finishLoading(data)
        })

    }

    /**
     * Handles the callback when a sync is performed
     */
    private fun finishLoading(result: JsonDataResult?) {

        // Can we process this request?
        if (result!!.wasSuccess && !result.hadError()) {
            this.runOnUiThread {
                this.finish()
            }
        }
    }

    /**
     * Writes data to the object
     */
    private fun flushDataToObject() {
        this.editItem!!.name = this.genericItemName.text.toString()
    }

    /**
     * Adds the item to the server
     */
    private fun addItemToServer() {
//        // Set the item id it doesn't exist
//        if (editItem!!.itemRefId == null) {
//            editItem!!.itemRefId = "item_" + RebarCoreCrypto.instance.randomAsString(16)
//        }

        // Flush data
        this.flushDataToObject()

        if (this.editItem!!.isNew) {
            // Add the item to the server
            this.appModel!!.addItem(this.editItem!!)
        }
        else {
            this.appModel!!.updateItem(this.editItem!!)
        }

    }

    /**
     * A option has been tapped
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.main_save -> {
                addItemToServer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Create the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_view_item, menu)
        return true
    }
    companion object {
        /**
         * Defines the static variable we will put the thread identifier in
         */
        const val ITEM_IDENTIFIER: String = "ITEM_IDENTIFIER"
        const val ITEM_CACHED_IDENTIFIER: String = "ITEM_CACHED_IDENTIFIER"

    }

}