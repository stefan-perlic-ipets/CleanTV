package com.perla.cleantv.app

import com.perla.cleantv.repository.http.ApiService
import com.perla.cleantv.BuildConfig
import com.perla.cleantv.repository.local.JsonReader
import com.perla.cleantv.repository.local.json.JsonReaderImpl
import com.perla.cleantv.repository.local.LocalChannelInfoRepositoryImpl
import com.perla.cleantv.repository.http.RetrofitService
import com.perla.cleantv.domain.GetChannelInfoUseCase
import com.perla.cleantv.domain.external.ChannelInfoRepository
import com.perla.cleantv.domain.external.Logger
import com.perla.cleantv.logger.LogcatLogger
import com.perla.cleantv.repository.remote.RemoteChannelInfoRepositoryImpl
import com.perla.cleantv.ui.view_model.TvViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<JsonReader> { JsonReaderImpl(get()) }
    single<ApiService> { RetrofitService.getService() }
    single<ChannelInfoRepository> {
        if (BuildConfig.Environment == "local") LocalChannelInfoRepositoryImpl(get()) else RemoteChannelInfoRepositoryImpl(
            get()
        )
    }
    single<GetChannelInfoUseCase> { GetChannelInfoUseCase(get()) }
    single<Logger> { LogcatLogger() }
    viewModel { TvViewModel(get(), get()) }

}
