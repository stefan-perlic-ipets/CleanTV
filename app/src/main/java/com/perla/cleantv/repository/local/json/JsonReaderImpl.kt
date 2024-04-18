package com.perla.cleantv.repository.local.json

import android.content.Context
import com.perla.cleantv.repository.local.JsonReader

class JsonReaderImpl(private val context: Context): JsonReader {

    override fun loadJsonFromAssets(fileName: String): Json {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return Json(String(buffer, Charsets.UTF_8))
    }

}