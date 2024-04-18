package com.perla.cleantv.repository.remote

import com.perla.cleantv.domain.ChannelInfo
import com.perla.cleantv.domain.Epg
import com.perla.cleantv.domain.Programme
import com.perla.cleantv.domain.external.EpgRepository

class EpgRepositoryImpl : BaseRepository(), EpgRepository {

    override fun getEpgForChannel(channelInfo: ChannelInfo): Epg {
        return Epg(listOf(Programme("1", "19.00", "21.00", "Star Wars - Poslednji Dzedaji")))
    }

}