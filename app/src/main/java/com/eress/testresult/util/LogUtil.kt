package com.eress.testresult.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.eress.testresult.BuildConfig
import com.eress.testresult.common.Common

object LogUtil {
    fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(Common.TAG, buildLogMsg(message))
        }
    }

    fun ts(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun tl(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun buildLogMsg(message: String): String {
        val ste = Thread.currentThread().stackTrace[4]
        val sb = StringBuilder()
        sb.append("[")
        sb.append(ste.fileName.replace(".java", ""))
        sb.append("::")
        sb.append(ste.methodName)
        sb.append("] ")
        sb.append(message)
        return sb.toString()
    }

    fun t(e: Throwable) {
        if (BuildConfig.DEBUG) {
            val sb = StringBuffer()
            try {
                sb.append(e.toString())
                sb.append("\n")
                val element = e.stackTrace
                for (i in element.indices) {
                    sb.append("\tat")
                    sb.append(element[i].toString())
                    sb.append("\n")
                }
            } catch (ex: Exception) {
                Log.e(Common.TAG, e.toString())
            }

            Log.e(Common.TAG, buildLogMsg(sb.toString())) // 오류 세부 내용 확인
//            Log.e(Common.TAG, buildLogMsg(e.toString()))  // 오류 기본 내용 확인
        }
    }
}
