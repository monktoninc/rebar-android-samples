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

package io.monkton.rebarsample.gcm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import io.monkton.rebar.push.PushNotificationHandler
import io.monkton.rebar.ui.startup.AppStartupActivity
import io.monkton.rebar.ui.RebarApplicationContext
import android.app.NotificationManager
import androidx.core.app.NotificationCompat

/**
 * Created by harold on 3/4/18.
 */
class SampleGCMHandler : PushNotificationHandler() {

    private val NOTIFICATION_ID: Int = 0x01;
    private val CHANNEL_ID: String = "SampleAppChannel";


    /**
     * Handles the incoming Firebase push notification message.
     * @param bundle
     */
    override fun handleMessage(bundle: Map<String, String>) {
        if (bundle.containsKey("alert")) sendNotification(bundle["alert"]!!)
    }

    /**
     * Displays a notification to the user
     */
    private fun sendNotification(message: String) {
        var soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var newIntent = Intent()
        newIntent.setClass(RebarApplicationContext.instance!!, AppStartupActivity::class.java)

        var pendingIntent = PendingIntent.getActivity(RebarApplicationContext.instance!!, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        var mBuilder = NotificationCompat.Builder(RebarApplicationContext.instance!!, CHANNEL_ID);
        //mBuilder.setSmallIcon(R.drawable.ic_launcher)
        mBuilder.setContentTitle("Messaging")
        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setContentText(message)
        mBuilder.setSound(soundUri)

        val notificationManager = RebarApplicationContext.instance!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build())

    }

}
