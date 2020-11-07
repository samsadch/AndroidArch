package com.samsad.devbitebackground.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samsad.devbitebackground.domain.Video

// TODO (01) Create the DatabaseEntities class, adding annotations for the class and the primary key.

@Entity
data class DatabaseVideo(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String
)

// TODO (02) Define extension function List<DatabaseVideo>.asDomainModel(), that returns
// a list of <Video>.
fun List<DatabaseVideo>.asDomainModel(): List<Video> {
    return map {
        Video(
            url = it.url,
            description = it.description,
            title = it.title,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }
}