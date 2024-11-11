package com.jdcastro.jairovideogames.dashboard.framework.data.implementations

import com.jdcastro.jairovideogames.base.Mapper
import com.jdcastro.jairovideogames.domain.models.Videogame
import com.jdcastro.jairovideogames.domain.models.VideogameResult
import com.jdcastro.jairovideogames.framework.data.config.response.Videogame as VideogameResponse
import javax.inject.Inject

class VideogamesMapper @Inject constructor() :
    Mapper<ArrayList<VideogameResponse>, VideogameResult> {

    override fun map(input: ArrayList<VideogameResponse>): VideogameResult {
        val videogameList = arrayListOf<Videogame>()
        input.forEach { _videogame ->
            videogameList.add(
                Videogame(
                    id = _videogame.id,
                    title = _videogame.title,
                    thumbnail = _videogame.thumbnail,
                    short_description =  _videogame.short_description,
                    game_url = _videogame.game_url,
                    genre = _videogame.genre,
                    platform = _videogame.platform,
                    publisher = _videogame.publisher,
                    developer = _videogame.developer,
                    release_date = _videogame.release_date,
                    freetogame_profile_url = _videogame.freetogame_profile_url
                )
            )
        }
        return VideogameResult(
            videogames = videogameList
        )
    }
}