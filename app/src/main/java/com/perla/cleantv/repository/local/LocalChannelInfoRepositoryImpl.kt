package com.perla.cleantv.repository.local

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.perla.cleantv.domain.CTVError
import com.perla.cleantv.domain.ChannelInfo
import com.perla.cleantv.domain.Result
import com.perla.cleantv.domain.external.ChannelInfoRepository
import com.perla.cleantv.repository.dto.ChannelInfoDto

class LocalChannelInfoRepositoryImpl(private val jsonReader: JsonReader) : ChannelInfoRepository {

    override suspend fun getChannelInfo(): Result<List<ChannelInfo>, CTVError> {
        val json = jsonReader.loadJsonFromAssets("channels.json")
        val listType = object : TypeToken<List<ChannelInfoDto>>() {}.type

        val channels: List<ChannelInfoDto> = Gson().fromJson(json.jsonContent, listType)
        return Result.Success(channels.mapIndexed { index, channelInfoDto ->
            ChannelInfo(
                index.toString(), channelInfoDto.channelUrl
            )
        })
    }
}
