package com.geekbrains.tests.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.tests.repository.GitHubApi
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.repository.RepositoryContract
import com.geekbrains.tests.utils.ScheduledProvider
import com.geekbrains.tests.utils.SchedulerImpl
import com.geekbrains.tests.view.search.MainActivity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel(
    private val repositoryContract: RepositoryContract = GitHubRepository(Retrofit.Builder()
        .baseUrl(MainActivity.BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubApi::class.java)),
    private val scheduledProvider: ScheduledProvider = SchedulerImpl()
) : ViewModel(){
    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData : LiveData<ScreenState> = _liveData
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })
    fun subscribeToLiveData() = liveData

    fun searchGitHub(searchQuery: String) {
        _liveData.value = ScreenState.Loading
        viewModelCoroutineScope.launch {
            val searchResponse = repositoryContract.searchGithubAsync(searchQuery)
            val searchResults = searchResponse.searchResults
            val totalCount = searchResponse.totalCount
            if (searchResults != null && totalCount != null) {
                _liveData.value = ScreenState.Working(searchResponse)
            } else {
                _liveData.value =
                    ScreenState.Error(Throwable("Search results or total count are null"))
            }
        }
    }

    private fun handleError(error: Throwable) {
        _liveData.value =
            ScreenState.Error(
                Throwable(
                    error.message ?: "Response is null or unsuccessful"
                )
            )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}