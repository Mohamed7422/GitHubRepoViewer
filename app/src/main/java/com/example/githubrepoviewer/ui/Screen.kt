package com.example.githubrepoviewer.ui

sealed class Screen(val route:String){
    object SplashScreen:Screen("splash_screen")

    object GitReposListScreen:Screen("repos_list")
    object GitRepoInfoScreen:Screen("repo_info")
    object GitRepoIssuesScreen:Screen("repo_issues")
}
