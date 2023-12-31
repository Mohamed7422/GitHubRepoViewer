package com.example.githubrepoviewer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubrepoviewer.ui.Screen
import com.example.githubrepoviewer.ui.SplashScreen
import com.example.githubrepoviewer.ui.git_repo_info_components.GitRepoInfoScreen
import com.example.githubrepoviewer.ui.git_repo_issues_components.GitRepoIssuesScreen
import com.example.githubrepoviewer.ui.git_repos_components.components.GitReposListScreen
import com.example.githubrepoviewer.ui.theme.GitHubRepoViewerTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubRepoViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {

                val navController = rememberNavController()
                NavHost(navController = navController,
                   startDestination = Screen.SplashScreen.route ){

                    composable(route = Screen.SplashScreen.route){
                        SplashScreen(navController)
                    }

                    composable(route = Screen.GitReposListScreen.route){
                        GitReposListScreen(navController)
                    }

                    composable(
                        route = Screen.GitRepoInfoScreen.route + "/{gitRepoOwner}"+"/{gitRepoName}"+"/{gitRepoId}",

                    ){
                          GitRepoInfoScreen(navController)
                    }

                    composable(route = Screen.GitRepoIssuesScreen.route+ "/{gitRepoOwner}"+"/{gitRepoName}"){
                        GitRepoIssuesScreen(navController)
                    }
                }


//                    var repoData  = remember {
//                        mutableStateOf("")
//                    }
//
//                    val databaseDao = GitRepoDataBase.LocalGitRepoDataBase.createGitRepoDao(this@MainActivity)
//
//                   LaunchedEffect(Unit ) {
//                        try {
//                            val response = GitReposApiClient.gitReposApiClient.getGitRepos()
//                            val responseDetails = GitReposApiClient.gitReposApiClient.getGitRepoDetails(response[0].owner?.login,response[0].name)
//
//                            databaseDao.insertGitRepo(responseDetails)
//                            val retrieveGitRepo = databaseDao.retrieveGitRepo(responseDetails.id)
//                            repoData.value  = retrieveGitRepo.toString()
//
//                            Log.i("MainActivity", "data $response")
//                            Log.i("MainActivity", "responseDetails $responseDetails")
//                            Log.i("MainActivity", "responseDetailsFromDataBase $retrieveGitRepo")
//
//
//
//                        }catch (e:Exception){
//                            Log.e("MainActivity", "Error fetching repo details ${e.localizedMessage}" )
//
//                        }
//                    }


                }
            }
        }
    }
}

