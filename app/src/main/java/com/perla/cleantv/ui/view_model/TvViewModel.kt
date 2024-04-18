package com.perla.cleantv.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perla.cleantv.domain.ChannelInfo
import com.perla.cleantv.domain.GetChannelInfoUseCase
import com.perla.cleantv.domain.external.Logger
import com.perla.cleantv.domain.fold
import kotlinx.coroutines.launch

class TvViewModel(
    private val getChannelInfoUseCase: GetChannelInfoUseCase,
    private val logger: Logger
) : ViewModel() {

    private val TAG = TvViewModel::class.java.name
    private val _channelNumber = MutableLiveData(0)
    val channelNumber: LiveData<Int> = _channelNumber
    var channelInfos: List<ChannelInfo> = arrayListOf()

    private val _isInitialized = MutableLiveData(false)
    val initialized: LiveData<Boolean> = _isInitialized

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    private val _infoPressed = MutableLiveData(false)
    val infoPressed: LiveData<Boolean> = _infoPressed

    fun initialize() {
        viewModelScope.launch {
            val channelInfoResult = getChannelInfoUseCase.getChannelInfo()
            channelInfoResult.fold(onSuccess = {
                channelInfos = it
            }) {

            }
            _isInitialized.value = true
        }
    }

    fun previousVideoPressed() {
        if (_channelNumber.value == 0) {
            _channelNumber.value = channelInfos.size - 1
            return
        }
        _channelNumber.value = _channelNumber.value?.minus(1)

        logger.debug(TAG, "Now playing channel number: ${_channelNumber.value}")
    }

    fun playPressed() {
        _isPlaying.value = !(_isPlaying.value ?: false)
    }

    fun nextPressed() {
        if (_channelNumber.value == channelInfos.size - 1) {
            _channelNumber.value = 0
            return
        }
        _channelNumber.value = _channelNumber.value?.plus(1)
        Log.d("TAG", "Now playing channel number: ${_channelNumber.value}")
    }

    fun expandInfo() {
        _infoPressed.value = true
    }

    fun collapseInfo() {
        _infoPressed.value = false
    }
}