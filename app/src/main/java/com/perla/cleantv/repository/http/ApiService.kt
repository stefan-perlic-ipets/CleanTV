package com.perla.cleantv.repository.http

import com.perla.cleantv.repository.dto.ChannelInfoDto
import retrofit2.http.GET


interface ApiService {

    @GET("channels/")
    suspend fun getChannelInfo(): List<ChannelInfoDto>?
}
