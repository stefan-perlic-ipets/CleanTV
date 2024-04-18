package com.perla.cleantv.repository.remote

import com.perla.cleantv.domain.CTVError
import com.perla.cleantv.domain.ChannelInfo
import com.perla.cleantv.domain.Result
import com.perla.cleantv.domain.external.ChannelInfoRepository
import com.perla.cleantv.repository.http.ApiService


class RemoteChannelInfoRepositoryImpl(val apiService: ApiService) : BaseRepository(),
    ChannelInfoRepository {

    override suspend fun getChannelInfo(): Result<List<ChannelInfo>, CTVError> {
        safeApiCall { apiService.getChannelInfo() }?.let {
            val mapIndexed: List<ChannelInfo> =
                it.mapIndexed { index, dto -> ChannelInfo(index.toString(), dto.channelUrl) }
            return Result.Success(mapIndexed)
        }
        return Result.Error(CTVError("Error message"))
    }
}

