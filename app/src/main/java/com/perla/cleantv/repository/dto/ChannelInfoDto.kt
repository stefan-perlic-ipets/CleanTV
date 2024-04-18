package com.perla.cleantv.repository.dto

import com.google.gson.annotations.SerializedName

class ChannelInfoDto(
    @SerializedName("id") val id: String,
    @SerializedName("channelUrl") val channelUrl: String
)
