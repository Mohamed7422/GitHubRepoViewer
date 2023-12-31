package com.example.githubrepoviewer.data

import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTOItem
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.User

object DummyGitRepoIssuesDTO {


    fun gitRepoIssuesDTO( ) = GitRepoIssuesDTO().apply {

        add(dummyGitRepoIssue1)
        add(dummyGitRepoIssue2)
    }

    private val dummyGitRepoIssue1 = GitRepoIssuesDTOItem(
        id = 1,
        activeLockReason = null,
        assignee = null,
        assignees = emptyList(),
        authorAssociation = "CONTRIBUTOR",
        body = "This is the issue body",
        closedAt = null,
        comments = 0,
        commentsUrl = "https://api.github.com/issues/1/comments",
        createdAt = "2023-01-01T00:00:00Z",
        draft = false,
        eventsUrl = "https://api.github.com/issues/1/events",
        htmlUrl = "https://github.com/user/repo/issues/1",
        labels = emptyList(),
        labelsUrl = "https://api.github.com/issues/1/labels",
        locked = false,
        milestone = null,
        nodeId = "node_id_1",
        number = 1,
        performedViaGithubApp = null,
        pullRequest = null,
        repositoryUrl = "https://api.github.com/repos/user/repo",
        state = "open",
        stateReason = null,
        timelineUrl = "https://api.github.com/issues/1/timeline",
        title = "Example Issue",
        updatedAt = "2023-01-02T00:00:00Z",
        url = "https://api.github.com/issues/1",
        user = User(
            avatar_url  = "https://avatars.githubusercontent.com/user1",
            events_url = "https://api.github.com/users/user1/events{/privacy}",
            followers_url = "https://api.github.com/users/user1/followers",
            following_url = "https://api.github.com/users/user1/following{/other_user}",
            gists_url = "https://api.github.com/users/user1/gists{/gist_id}",
            gravatar_id = "",
            html_url = "https://github.com/user1",
            id = 123,
            login = "user1",
            node_id = "node_id_user1",
            organizations_url = "https://api.github.com/users/user1/orgs",
            received_events_url = "https://api.github.com/users/user1/received_events",
            repos_url = "https://api.github.com/users/user1/repos",
            site_admin = false,
            starred_url = "https://api.github.com/users/user1/starred{/owner}{/repo}",
            subscriptions_url = "https://api.github.com/users/user1/subscriptions",
            type = "User",
            url = "https://api.github.com/users/user1"
        )
    )
    private val dummyGitRepoIssue2 = GitRepoIssuesDTOItem(
        id = 2,
        activeLockReason = null,
        assignee = null,
        assignees = emptyList(),
        authorAssociation = "CONTRIBUTOR",
        body = "This is the issue body",
        closedAt = null,
        comments = 0,
        commentsUrl = "https://api.github.com/issues/1/comments",
        createdAt = "2023-01-01T00:00:00Z",
        draft = false,
        eventsUrl = "https://api.github.com/issues/1/events",
        htmlUrl = "https://github.com/user/repo/issues/1",
        labels = emptyList(),
        labelsUrl = "https://api.github.com/issues/1/labels",
        locked = false,
        milestone = null,
        nodeId = "node_id_1",
        number = 1,
        performedViaGithubApp = null,
        pullRequest = null,
        repositoryUrl = "https://api.github.com/repos/user/repo",
        state = "open",
        stateReason = null,
        timelineUrl = "https://api.github.com/issues/1/timeline",
        title = "Example Issue",
        updatedAt = "2023-01-02T00:00:00Z",
        url = "https://api.github.com/issues/1",
        user = User(
            avatar_url  = "https://avatars.githubusercontent.com/user1",
            events_url = "https://api.github.com/users/user1/events{/privacy}",
            followers_url = "https://api.github.com/users/user1/followers",
            following_url = "https://api.github.com/users/user1/following{/other_user}",
            gists_url = "https://api.github.com/users/user1/gists{/gist_id}",
            gravatar_id = "",
            html_url = "https://github.com/user1",
            id = 123,
            login = "user1",
            node_id = "node_id_user1",
            organizations_url = "https://api.github.com/users/user1/orgs",
            received_events_url = "https://api.github.com/users/user1/received_events",
            repos_url = "https://api.github.com/users/user1/repos",
            site_admin = false,
            starred_url = "https://api.github.com/users/user1/starred{/owner}{/repo}",
            subscriptions_url = "https://api.github.com/users/user1/subscriptions",
            type = "User",
            url = "https://api.github.com/users/user1"
        )
    )
}