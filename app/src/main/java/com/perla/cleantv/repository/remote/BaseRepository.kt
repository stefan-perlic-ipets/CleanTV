package com.perla.cleantv.repository.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class BaseRepository {
    suspend fun <T> safeApiCall(call: suspend () -> T): T? = withContext(Dispatchers.IO) {
        try {
            call()
        } catch (exception: Exception) {
            null
        }
    }
}
