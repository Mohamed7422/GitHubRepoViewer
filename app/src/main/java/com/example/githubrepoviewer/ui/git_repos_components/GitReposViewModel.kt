package com.example.githubrepoviewer.ui.git_repos_components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem
import com.example.githubrepoviewer.domain.use_case.GetGitReposListUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class GitReposViewModel(
    private val getGitReposListUseCase: GetGitReposListUseCase
) : ViewModel() {


    private val _state: MutableStateFlow<PagingData<GitReposDTOItem>> =
        MutableStateFlow(value = PagingData.empty())

    private val _allRepos = MutableStateFlow<PagingData<GitReposDTOItem>>(PagingData.empty())
    private val _filteredRepos = MutableStateFlow<PagingData<GitReposDTOItem>>(PagingData.empty())

    val state: StateFlow<PagingData<GitReposDTOItem>> = _filteredRepos.asStateFlow()


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    private val debouncedSearchQuery = _searchQuery.debounce(1000)


    init {
        loadAllRepos()
        viewModelScope.launch {
            debouncedSearchQuery.collect { query ->
                applyFilter(query)
            }
        }

    }

    private fun loadAllRepos() = viewModelScope.launch {
        getGitReposListUseCase.execute()
            .cachedIn(this)
            .collect { allRepos ->
                _allRepos.value = allRepos
                applyFilter(_searchQuery.value) // Initial filter application
            }
    }

    private fun applyFilter(query: String) {
        val filteredRepos = if (query.isBlank()) {
            _allRepos.value
        } else {
            _allRepos.value.filter {
                it.name!!.contains(query, ignoreCase = true) ||
                        it.owner?.login?.contains(query, ignoreCase = true) == true
            }
        }
        _filteredRepos.value = filteredRepos
    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

    }


    class GitReposViewModelFactory(private val useCase: GetGitReposListUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(GitReposViewModel::class.java)) {
                GitReposViewModel(useCase) as T
            } else {
                throw IllegalArgumentException("GitReposViewModel class not found")
            }
        }
    }

}


