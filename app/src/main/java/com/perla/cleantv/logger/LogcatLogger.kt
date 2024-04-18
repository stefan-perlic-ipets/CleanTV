package com.perla.cleantv.logger

import android.util.Log
import com.perla.cleantv.domain.external.Logger

class LogcatLogger : Logger {

    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun warning(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun error(tag: String, message: String) {
        Log.e(tag, message)
    }
}