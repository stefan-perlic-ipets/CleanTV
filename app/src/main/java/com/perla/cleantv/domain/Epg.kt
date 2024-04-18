package com.perla.cleantv.domain

data class Epg(val programmes: List<Programme>)
data class Programme(val id: String, val startTime: String, val endTime: String, val contentName: String)
