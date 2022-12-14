/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.network.MarsApi
import kotlinx.coroutines.launch

/**
 * El [ViewModel] que se adjunta al [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // El MutableLiveData interno que almacena el estado de la solicitud más reciente
    private val _status = MutableLiveData<String>()

    // El LiveData inmutable externo para el estado de la solicitud
    val status: LiveData<String> = _status
    /**
     * Llame a getMarsPhotos() en init para que podamos mostrar el estado inmediatamente.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Obtiene información de fotos de Mars del servicio Mars API Retrofit y actualiza a
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                _status.value = "Success: ${listResult.size} Mars photos retrieved"
            } catch (e: Exception){
                _status.value = "Failure: ${e.message}"
            }

        }
    }
}
