package com.example.githubrepoviewer.data.network.dto.repo_issues_dto

import com.google.gson.annotations.SerializedName


data class GitRepoIssuesDTOItem(
    val id: Int,
    @SerializedName("active_lock_reason")
    val activeLockReason: Any?,
    val assignee: Any?,
    val assignees: List<Any>,
    @SerializedName("author_association")
    val authorAssociation: String,
    val body: String?,
    @SerializedName("closed_at")
    val closedAt: Any?,
    val comments: Int,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    val draft: Boolean?,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val labels: List<Any>,
    @SerializedName("labels_url")
    val labelsUrl: String,
    val locked: Boolean,
    val milestone: Any?,
    @SerializedName("node_id")
    val nodeId: String,
    val number: Int,
    @SerializedName("performed_via_github_app")
    val performedViaGithubApp: Any?,
    @SerializedName("pull_request")
    val pullRequest: PullRequest?,
    @SerializedName("repository_url")
    val repositoryUrl: String,
    val state: String,
    @SerializedName("state_reason")
    val stateReason: Any?,
    @SerializedName("timeline_url")
    val timelineUrl: String,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String,
    val user: User
)