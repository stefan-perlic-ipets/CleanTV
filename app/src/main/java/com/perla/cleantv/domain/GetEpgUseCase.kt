package com.perla.cleantv.domain

import com.perla.cleantv.domain.external.EpgRepository

class GetEpgUseCase(private val epgRepository: EpgRepository) {

    fun getEpg(channelInfo: ChannelInfo): Epg {
        return epgRepository.getEpgForChannel(channelInfo)
    }
}