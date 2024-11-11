package com.jdcastro.jairovideogames.data.repository

import com.jdcastro.jairovideogames.data.dataSource.VideogameDataSource
import com.jdcastro.jairovideogames.data.database.dao.VideogameDAO
import com.jdcastro.jairovideogames.data.database.entities.VideogameEntity
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import com.jdcastro.jairovideogames.domain.models.toDomainObj
import javax.inject.Inject
import kotlin.collections.ArrayList

class VideogameRepository @Inject constructor(
    private val videogameDataSource: VideogameDataSource,
    private val videogameDAO: VideogameDAO
) {
    suspend fun getVideogamesFromApi(): ArrayList<VideogameObj> {
        val response = videogameDataSource.getVideogames()
        return Result.success(response).getOrNull()?.getOrNull()?.videogames?.map { it.toDomainObj() } as ArrayList<VideogameObj>
    }

    suspend fun getVideogamesFromDatabase(): ArrayList<VideogameObj> {
        val response = videogameDAO.getAllVideogames()
        return response.map { it.toDomainObj() } as ArrayList<VideogameObj>
    }

    suspend fun getVideogamesSearchByTitle(title: String): ArrayList<VideogameObj> {
        val response = videogameDAO.getVideogamesSearchByTitle(title.plus("%"))
        return response.map { it.toDomainObj() } as ArrayList<VideogameObj>
    }

    suspend fun getVideogameDetail(id: Int): VideogameObj {
        return videogameDAO.getVideogameDetail(id).toDomainObj()
    }

    suspend fun insertVideogames(videogameEntity: List<VideogameEntity>) {
        videogameDAO.insertAll(videogameEntity)
    }

    suspend fun deleteVideogame(item: VideogameEntity) {
        videogameDAO.deleteVideogame(item)
    }

    suspend fun getCategories(): ArrayList<String> {
        return videogameDAO.getCategories() as ArrayList<String>
    }

    suspend fun getVideogamesByCategory(category: String): ArrayList<VideogameObj> {
        val response = videogameDAO.getVideogamesByCategory(category)
        return response.map { it.toDomainObj() } as ArrayList<VideogameObj>
    }
}