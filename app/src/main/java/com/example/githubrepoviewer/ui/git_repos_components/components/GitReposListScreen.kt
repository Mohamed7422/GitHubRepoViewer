package com.example.githubrepoviewer.ui.git_repos_components.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.data.localdatabase.GitRepoDataBase
import com.example.githubrepoviewer.data.network.GitReposApiClient
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem
import com.example.githubrepoviewer.data.repoImp.GitReposListRepoImpl
import com.example.githubrepoviewer.domain.use_case.GetGitReposListUseCase
import com.example.githubrepoviewer.ui.Screen
import com.example.githubrepoviewer.ui.git_repos_components.GitReposViewModel
import com.example.githubrepoviewer.ui.git_repos_components.utils.ErrorMessage
import com.example.githubrepoviewer.ui.git_repos_components.utils.LoadingNextPageItem
import com.example.githubrepoviewer.ui.git_repos_components.utils.PageLoader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitReposListScreen(
     navController: NavController,
     viewModelFactory: GitReposViewModel.GitReposViewModelFactory = GitReposViewModel.GitReposViewModelFactory(
         GetGitReposListUseCase(GitReposListRepoImpl.getInstance(
             GitReposApiClient.gitReposApiClient,GitRepoDataBase.LocalGitRepoDataBase.createGitRepoDao(
             LocalContext.current)))
     )

) {
    val viewModel:GitReposViewModel = viewModel(factory = viewModelFactory)

    val gitReposPagingItems : LazyPagingItems<GitReposDTOItem> =
                                      viewModel.state.collectAsLazyPagingItems()

    val searchQuery = remember { mutableStateOf("") }

    Column {
        TextField(
            value = searchQuery.value,
            onValueChange = {
                            searchQuery.value = it
                viewModel.onSearchQueryChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black, shape = RoundedCornerShape(4.dp)),
            placeholder = { Text("Search") },
            textStyle = TextStyle(color = if (isSystemInDarkTheme()) Color.White else Color.Black),
            singleLine = true
        )

        LazyColumn(modifier = Modifier.padding(10.dp)){




            items(gitReposPagingItems.itemCount){ index ->
                GitReposScreenItem(gitReposPagingItems[index]!!,navController)
            }

            gitReposPagingItems.apply {
                when{
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error ->{
                        val error = gitReposPagingItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = stringResource(R.string.check_internet_connection),
                                onClickRetry = { retry() })
                        }

                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = gitReposPagingItems.loadState.append as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier,
                                message = stringResource(R.string.check_internet_connection),
                                onClickRetry = { retry() })
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun GitReposScreenItem(repoItem: GitReposDTOItem,navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()){

               GitReposListItem(gitRepoItem = repoItem,
                   onItemCLick = {
                       navController.navigate(Screen.GitRepoInfoScreen.route
                               +"/${repoItem.owner?.login}"+"/${repoItem.name}"+"/${repoItem.id}")
                       Log.i("ReposScreen"," gitRepoItem id${repoItem.id}")
                   })
         }

}


