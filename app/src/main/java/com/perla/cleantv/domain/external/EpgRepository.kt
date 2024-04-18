package com.perla.cleantv.domain.external

import com.perla.cleantv.domain.ChannelInfo
import com.perla.cleantv.domain.Epg

interface EpgRepository {
    fun getEpgForChannel(channelInfo: ChannelInfo): Epg
}