package com.perla.cleantv.domain

import com.perla.cleantv.domain.external.ChannelInfoRepository

class GetChannelInfoUseCase(private val channelInfoRepository: ChannelInfoRepository) {

    suspend fun getChannelInfo(): Result<List<ChannelInfo>, CTVError> {
        return channelInfoRepository.getChannelInfo()
    }
}