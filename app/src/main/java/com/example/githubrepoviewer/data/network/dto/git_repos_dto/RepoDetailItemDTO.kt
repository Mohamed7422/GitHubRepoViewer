package com.example.githubrepoviewer.data.network.dto.git_repos_dto

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "repository")
@Parcelize
data class RepoDetailItemDTO(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("default_branch")
    @ColumnInfo(name = "defaultBranch")
    val defaultBranch: String,

    val description: String?,

    val forks: Int,

    @SerializedName("forks_count")
    @ColumnInfo(name = "forksCount")
    val forksCount: Int,

    @SerializedName("full_name")
    @ColumnInfo(name = "fullName")
    val fullName: String,

    val language: String?,

    val name: String,

    @SerializedName("network_count")
    @ColumnInfo(name = "networkCount")
    val networkCount: Int,

    @SerializedName("node_id")
    @ColumnInfo(name = "nodeId")
    val nodeId: String,

    @SerializedName("open_issues")
    @ColumnInfo(name = "openIssues")
    val openIssues: Int,

    @SerializedName("open_issues_count")
    @ColumnInfo(name = "openIssuesCount")
    val openIssuesCount: Int,

    @ColumnInfo(name = "owner")
    val owner: Owner,

    val size: Int,

    @SerializedName("stargazers_count")
    @ColumnInfo(name = "stargazersCount")
    val stargazersCount: Int,

    @SerializedName("subscribers_count")
    @ColumnInfo(name = "subscribersCount")
    val subscribersCount: Int,

    val visibility: String,

    val watchers: Int,

    @SerializedName("watchers_count")
    @ColumnInfo(name = "watchersCount")
    val watchersCount: Int
) : Parcelable


class OwnerTypeConvertor{

    @TypeConverter
    fun fromOwner(owner: Owner?):String?{
        val gson = Gson()
        return owner?.let { gson.toJson(it) }
    }
    @TypeConverter
    fun toOwner(ownerString:String?): Owner?{
        val gson = Gson()
        return ownerString?.let { gson.fromJson(it, Owner::class.java) }
    }


}