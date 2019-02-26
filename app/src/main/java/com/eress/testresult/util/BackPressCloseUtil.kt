package com.eress.testresult.util

import android.app.Activity
import com.eress.testresult.R

class BackPressCloseUtil(private val activity: Activity) {
    private var backKeyPressedTime: Long = 0

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            LogUtil.ts(activity.applicationContext, activity.getString(R.string.back_key_message))
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish()
        }
    }
}
