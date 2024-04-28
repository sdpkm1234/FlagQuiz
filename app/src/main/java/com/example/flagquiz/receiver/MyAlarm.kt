package com.example.flagquiz.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


open class MyAlarm : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        val intnt = Intent("com.example.flagquiz")

        context.sendBroadcast(intnt)

    }
}
