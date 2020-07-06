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

package io.monkton.rebarsample.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import io.monkton.rebarsample.R
import io.monkton.rebarsample.business.data.GenericItem

class GenericItemRecyclerViewAdapter(private val mListener: ((item: GenericItem?) -> Unit)?)
    : RecyclerView.Adapter<GenericItemRecyclerViewAdapter.ViewHolder>() {

    fun add(messages: List<GenericItem>?) {
        inboxMessages.addAll(messages!!)
        this.notifyDataSetChanged()
    }

    fun replace(messages: List<GenericItem>?) {
        inboxMessages.clear()
        inboxMessages.addAll(messages!!)
        this.notifyDataSetChanged()
    }

    private var inboxMessages: ArrayList<GenericItem> = arrayListOf()

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as GenericItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.invoke(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_generic_item, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = inboxMessages[position]
        holder.genericItemName.text = item.name

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = inboxMessages.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val genericItemName: TextView = mView.findViewById(R.id.generic_item_name_label)

        override fun toString(): String {
            return super.toString() + " '" + genericItemName.text + "'"
        }
    }
}