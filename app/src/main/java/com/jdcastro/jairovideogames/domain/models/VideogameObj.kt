package com.jdcastro.jairovideogames.domain.models

import com.jdcastro.jairovideogames.data.database.entities.VideogameEntity

data class VideogameObj(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val freetogame_profile_url: String
)

fun VideogameEntity.toDomainObj() = VideogameObj(
    id,
    title,
    thumbnail,
    short_description,
    game_url,
    genre,
    platform,
    publisher,
    developer,
    release_date,
    freetogame_profile_url
)

fun Videogame.toDomainObj() = VideogameObj(
    id,
    title,
    thumbnail,
    short_description,
    game_url,
    genre,
    platform,
    publisher,
    developer,
    release_date,
    freetogame_profile_url
)
