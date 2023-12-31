package com.example.githubrepoviewer.ui.git_repo_issues_components

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubrepoviewer.MainCoroutineRule
import com.example.githubrepoviewer.data.DummyGitRepoIssuesDTO
import com.example.githubrepoviewer.data.DummyGitReposDTO
import com.example.githubrepoviewer.data.DummyRepoDetailItemDTO
import com.example.githubrepoviewer.data.localdatabase.GitRepoDao
import com.example.githubrepoviewer.data.network.GitReposApiService
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTO
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import com.example.githubrepoviewer.data.repoImp.GitReposListRepoImpl
import com.example.githubrepoviewer.domain.use_case.GetGitRepoIssuesUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class GitRepoIssuesViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private val testScope = TestScope(mainCoroutineRule.testScheduler)

   /*
   * For Testing kindly change the api client in GitReposListRepoImpl when you test each function:
   * For fetchDataState_isSetCorrectly() -> TestApiClient() which is currently the default
   * For loadingAtFetchingDataState_isSetCorrectly() -> TestApiClientWithDelay()
   * */
    @Test
    fun fetchDataState_isSetCorrectly() = testScope.runTest{

        val viewModel = getViewModel()

        val gitRepoIssueState = viewModel.gitRepoIssueState.value


        assertTrue(
            gitRepoIssueState == GitRepoIssueState(
                isLoading = false,
                repoIssue =  DummyGitRepoIssuesDTO.gitRepoIssuesDTO(),
                error = ""
            )
        )
    }

    @Test
    fun loadingAtFetchingDataState_isSetCorrectly() = testScope.runTest{

        val viewModel = getViewModel()

        val gitRepoIssueState = viewModel.gitRepoIssueState.value

        assertTrue(gitRepoIssueState == GitRepoIssueState(
             isLoading = true,
             repoIssue = GitRepoIssuesDTO(),
             error = ""
        )
        )
    }


    private fun getViewModel(): GitRepoIssuesViewModel {
        val repo = GitReposListRepoImpl(TestApiClient(),TestGitRepoDao())
        val gitRepoIssuesUseCase = GetGitRepoIssuesUseCase(repo)
        val savedStateHandle = SavedStateHandle()
        return GitRepoIssuesViewModel(gitRepoIssuesUseCase,savedStateHandle)
    }

    class TestApiClient: GitReposApiService {

        override suspend fun getGitRepos(pageNumber: Int): GitReposDTO {
            return DummyGitReposDTO.gitReposDTO
        }

        override suspend fun getGitRepoDetails(owner: String?, repo: String?): RepoDetailItemDTO {
            return DummyRepoDetailItemDTO.getDummyRepoDetailsItem()
        }

        override suspend fun getGitRepoIssues(owner: String?, repo: String?): GitRepoIssuesDTO {
           return DummyGitRepoIssuesDTO.gitRepoIssuesDTO()
        }

    }

    class TestApiClientWithDelay: GitReposApiService {
        override suspend fun getGitRepos(pageNumber: Int): GitReposDTO {
            return DummyGitReposDTO.gitReposDTO
        }

        override suspend fun getGitRepoDetails(owner: String?, repo: String?): RepoDetailItemDTO {
            return DummyRepoDetailItemDTO.getDummyRepoDetailsItem()
        }


        override suspend fun getGitRepoIssues(owner: String?, repo: String?): GitRepoIssuesDTO {
            delay(5000)
            return DummyGitRepoIssuesDTO.gitRepoIssuesDTO()
        }
    }

    class TestGitRepoDao: GitRepoDao {

        private val repoStorage = mutableMapOf<Int, RepoDetailItemDTO>()

        override suspend fun insertGitRepo(repoDetailItemDTO: RepoDetailItemDTO) {
            repoDetailItemDTO.id?.let { id ->
                repoStorage[id] = repoDetailItemDTO
            }
        }

        override suspend fun retrieveGitRepo(repoId: Int?): RepoDetailItemDTO {
            return repoId?.let { id ->
                repoStorage[id] ?: throw IllegalArgumentException("Repo not found with id: $id")
            } ?: throw IllegalArgumentException("RepoId is null")
        }

        override suspend fun retrieveGitRepoByOwnerName(ownerName: String?): RepoDetailItemDTO {
            return repoStorage.values.find { it.owner.login == ownerName }
                ?: throw IllegalArgumentException("Repo not found with owner: $ownerName")
        }

        override suspend fun updateGitRepo(repoDetailItemDTO: RepoDetailItemDTO) {
            repoDetailItemDTO.id?.let { id ->
                if (repoStorage.containsKey(id)) {
                    repoStorage[id] = repoDetailItemDTO
                } else {
                    throw IllegalArgumentException("Repo not found with id: $id")
                }
            } ?: throw IllegalArgumentException("RepoId is null")
        }

        override suspend fun deleteGitRepo(id: Int?) {
            id?.let {
                if (repoStorage.containsKey(id)) {
                    repoStorage.remove(id)
                } else {
                    throw IllegalArgumentException("Repo not found with id: $id")
                }
            } ?: throw IllegalArgumentException("RepoId is null")
        }

    }

}