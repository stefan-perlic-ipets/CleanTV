package com.perla.cleantv.domain.external

interface Logger {
    fun debug(tag: String = "CleanTv", message: String)
    fun warning(tag: String = "CleanTv", message: String)
    fun error(tag: String = "CleanTv", message: String)

}