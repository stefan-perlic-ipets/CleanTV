package com.perla.cleantv.domain.external

import com.perla.cleantv.domain.CTVError
import com.perla.cleantv.domain.ChannelInfo
import com.perla.cleantv.domain.Result

interface ChannelInfoRepository {
    suspend fun getChannelInfo(): Result<List<ChannelInfo>, CTVError>
}
