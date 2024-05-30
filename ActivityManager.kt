package com.caption.mprint.manager

import android.app.Activity
import java.lang.ref.WeakReference

object ActivityManager {
    var sCurrActivityWeakRef: WeakReference<Activity>? = null

    fun setCurrActivity(activity: Activity) {
        sCurrActivityWeakRef = WeakReference<Activity>(activity)
    }

    fun getCurrActivity(): Activity? {
        return if (sCurrActivityWeakRef == null) {
            null
        } else {
            sCurrActivityWeakRef?.get()
        }
    }
}