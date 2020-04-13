package com.worldofplay.app.intent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class IntentResolverService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null) {
            return
        }
        if (intent.hasExtra("ACTION") == true) {
            val action = intent.getStringExtra("ACTION")
            when (action) {
                "HOME" -> {
                    val intentAction = Intent()
                    intentAction.setAction("com.worldofplay.app.action.home")
                    intentAction.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intentAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intentAction)
                }
            }
        }
    }

}