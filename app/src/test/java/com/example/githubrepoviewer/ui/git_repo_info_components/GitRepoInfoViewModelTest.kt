package com.example.githubrepoviewer.ui.git_repo_info_components

import androidx.lifecycle.SavedStateHandle
import com.example.githubrepoviewer.MainCoroutineRule
import com.example.githubrepoviewer.data.DummyGitReposDTO
import com.example.githubrepoviewer.data.DummyRepoDetailItemDTO
import com.example.githubrepoviewer.data.localdatabase.GitRepoDao
import com.example.githubrepoviewer.data.network.GitReposApiService
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTO
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import com.example.githubrepoviewer.data.repoImp.GitReposListRepoImpl
import com.example.githubrepoviewer.domain.use_case.GetGitRepoInfoUseCase
import com.example.githubrepoviewer.domain.use_case.GetRepoInfoFromDataBaseUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test



@ExperimentalCoroutinesApi
class GitRepoInfoViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private val testScope = TestScope(mainCoroutineRule.testScheduler)



    @Test
    fun fetchDataState_isSetCorrectly() = testScope.runTest{

          val viewModel = getViewModel()

          val gitRepoInfoItemState = viewModel.gitRepoInfoItemState.value

          assertTrue(gitRepoInfoItemState == GitRepoInfoState(
              isLoading = false,
              repoDetailItemDTO = DummyRepoDetailItemDTO.getDummyRepoDetailsItem(),
              error = ""
          ))
      }

    @Test
    fun fetchDataErrorState_isSetCorrectly()= testScope.runTest {
        val viewModel = getViewModel()
        viewModel.getRepoInfoFromDataBase(null){
            hasData -> }
        val gitRepoInfoItemState = viewModel.gitRepoInfoItemState.value

        assertTrue(gitRepoInfoItemState == GitRepoInfoState(
            isLoading = false,
            repoDetailItemDTO = null,
            error = gitRepoInfoItemState.error
        ))
        }


    private fun getViewModel(): GitRepoInfoViewModel {

        val repo = GitReposListRepoImpl(TestApiClient(),TestGitRepoDao())
        val savedStateHandle =SavedStateHandle()
        val gitRepoInfoFromDataBaseUseCase = GetRepoInfoFromDataBaseUseCase(repo)
        val gitRepoInfoUseCase = GetGitRepoInfoUseCase(repo)
        return GitRepoInfoViewModel(savedStateHandle = savedStateHandle, gitRepoInfoUseCase = gitRepoInfoUseCase,
            gitRepoInfoFromDataBaseUseCase = gitRepoInfoFromDataBaseUseCase)
    }


    class TestApiClient: GitReposApiService{

        override suspend fun getGitRepos(pageNumber: Int): GitReposDTO {
            return DummyGitReposDTO.gitReposDTO
        }

        override suspend fun getGitRepoDetails(owner: String?, repo: String?): RepoDetailItemDTO {
           return DummyRepoDetailItemDTO.getDummyRepoDetailsItem()
        }

        override suspend fun getGitRepoIssues(owner: String?, repo: String?): GitRepoIssuesDTO {
            TODO("Not yet implemented")
        }

    }

    class TestGitRepoDao:GitRepoDao{

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