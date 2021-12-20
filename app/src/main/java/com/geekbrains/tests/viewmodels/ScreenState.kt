package com.geekbrains.tests.viewmodels

import com.geekbrains.tests.model.SearchResponse

sealed class ScreenState {
    object Loading : ScreenState()
    data class Working(val searchResponse: SearchResponse) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}