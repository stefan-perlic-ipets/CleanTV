package com.perla.cleantv.repository.local

import com.perla.cleantv.repository.local.json.Json

interface JsonReader {

    fun loadJsonFromAssets(fileName: String): Json
}