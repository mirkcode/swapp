package com.mvaresedev.swapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mvaresedev.swapp.data.db.StarWarsDatabase
import com.mvaresedev.swapp.data.db.entities.CharacterEntity
import com.mvaresedev.swapp.data.db.entities.RemoteKeyEntity
import com.mvaresedev.swapp.data.network.StarWarsApi
import com.mvaresedev.swapp.domain.mapper.DbMapper
import com.mvaresedev.swapp.domain.mapper.NetworkMapper

@ExperimentalPagingApi
class PageKeyedRemoteMediator(
    private val starWarsApi: StarWarsApi,
    private val networkMapper: NetworkMapper,
    private val dbMapper: DbMapper,
    private val starWarsDatabase: StarWarsDatabase,
    private val initialUrl: String
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, CharacterEntity>): MediatorResult {
        return try {
            val apiUrl: String = when(loadType){
                LoadType.REFRESH -> initialUrl
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastRemoteKey = getLastRemoteKeys()
                    if(lastRemoteKey?.next != null)
                        lastRemoteKey.next
                    else
                        return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val listResponse = starWarsApi.getPeopleListByUrl(apiUrl)
            val characterList = networkMapper.mapListResponseToDomain(listResponse)

            starWarsDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    starWarsDatabase.getCharacterDao().clearAllCharacters()
                    starWarsDatabase.getRemoteKeyDao().clearAllKeys()
                }
                starWarsDatabase.getCharacterDao().insertAllCharacters(dbMapper.mapCharacterDomainToEntities(characterList))
                starWarsDatabase.getRemoteKeyDao().insertKey(RemoteKeyEntity(0, listResponse.next))
            }

            MediatorResult.Success(endOfPaginationReached = listResponse.next == null)

        } catch (exception: Exception) {
            //if some items present, avoid to propagate error and load anyway from local db
            if(loadType == LoadType.REFRESH && starWarsDatabase.getCharacterDao().getCharactersCount() > 0)
                MediatorResult.Success(endOfPaginationReached = false)
            else
                MediatorResult.Error(exception)
        }
    }

    private suspend fun getLastRemoteKeys(): RemoteKeyEntity? {
        return starWarsDatabase.getRemoteKeyDao().getAllRemoteKeys().lastOrNull()
    }

}