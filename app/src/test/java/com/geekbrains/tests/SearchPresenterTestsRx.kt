package com.geekbrains.tests

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.repository.RepositoryContract
import com.geekbrains.tests.view.search.ViewSearchContract
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.inOrder
import org.mockito.MockitoAnnotations

class SearchPresenterTestsRx {
    private lateinit var searchPresenter: SearchPresenter

    @Mock
    private lateinit var gitHubRepository: RepositoryContract

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchPresenter(viewContract, gitHubRepository, SchedulerProviderStub())
    }

    @Test
    fun searchGithub_test() {
        `when`(gitHubRepository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    1,
                    listOf()
                )
            )
        )
        searchPresenter.searchGitHub(SEARCH_QUERY)
        verify(gitHubRepository, atLeastOnce()).searchGithub(SEARCH_QUERY)
    }

    @Test
    fun handleRequest_Error() {
        `when`(gitHubRepository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.error(Throwable(ERROR_TEXT))
        )
        searchPresenter.searchGitHub(SEARCH_QUERY)
        verify(viewContract, atLeastOnce()).displayError(ERROR_TEXT)
    }

    @Test
    fun handleResponseError_TotalCountIsNull() {
        `when`(gitHubRepository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    null,
                    listOf()
                )
            )
        )

        searchPresenter.searchGitHub(SEARCH_QUERY)
        verify(viewContract, atLeastOnce()).displayError("Response is null")
    }

    @Test
    fun handleResponseError_TotalCountIsNull_ViewContractMethodOrder() {
        `when`(gitHubRepository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    null,
                    listOf()
                )
            )
        )

        searchPresenter.searchGitHub(SEARCH_QUERY)

        val inOrder = inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(true)
        inOrder.verify(viewContract).displayError("Response is null")
        inOrder.verify(viewContract).displayLoading(false)
    }

    @Test
    fun handleResponseSuccess() {
        `when`(gitHubRepository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    42,
                    listOf()
                )
            )
        )

        searchPresenter.searchGitHub(SEARCH_QUERY)
        verify(viewContract, atLeastOnce()).displaySearchResults(listOf(), 42)
    }

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
    }
}