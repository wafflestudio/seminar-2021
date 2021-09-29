package com.wafflestudio.assignment3skeleton.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Already Completed
@JsonClass(generateAdapter = true)
@Entity(tableName = "member_table")
data class Member(
    @Json(name = "id")
    @ColumnInfo(name = "id") @PrimaryKey
    var id: Int,
    @Json(name = "name")
    @ColumnInfo(name = "name")
    var name: String,
    @Json(name = "team")
    @ColumnInfo(name = "team")
    var team: String,
    @Json(name = "profile_image")
    @Ignore
    var profileImage: String?,
    @Json(name = "lectures")
    @Ignore
    var lectures: List<Lecture>?
) {
    constructor():this(0, "", "", null, null)
}
