package com.mvaresedev.swapp.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.room.withTransaction
import com.mvaresedev.swapp.data.PageKeyedRemoteMediator
import com.mvaresedev.swapp.data.db.StarWarsDatabase
import com.mvaresedev.swapp.data.network.StarWarsApi
import com.mvaresedev.swapp.data.network.models.FilmDetailResponse
import com.mvaresedev.swapp.data.network.models.VehicleDetailResponse
import com.mvaresedev.swapp.domain.mapper.DbMapper
import com.mvaresedev.swapp.domain.mapper.NetworkMapper
import com.mvaresedev.swapp.domain.models.Film
import com.mvaresedev.swapp.domain.models.Vehicle
import com.mvaresedev.swapp.domain.repo.StarWarsRepository
import com.mvaresedev.swapp.utils.ResultWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import java.lang.Exception

class StarWarsRepositoryImpl(
    private val starWarsApi: StarWarsApi,
    private val starWarsDatabase: StarWarsDatabase,
    private val networkMapper: NetworkMapper,
    private val dbMapper: DbMapper
) : StarWarsRepository {

    companion object {
        private const val PAGE_SIZE = 10
        private const val PREFETCH_DISTANCE = 2
        private const val INIT_URL = "https://swapi.dev/api/people/"
    }

    @ExperimentalPagingApi
    override fun retrieveCharacters() = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, prefetchDistance = PREFETCH_DISTANCE),
        remoteMediator = PageKeyedRemoteMediator(starWarsApi, networkMapper, dbMapper, starWarsDatabase, INIT_URL),
        pagingSourceFactory = { starWarsDatabase.getCharacterDao().getAllCharacters() }
    ).flow.map { pagingData ->
        pagingData.map {  characterEntity ->
            dbMapper.mapCharacterEntityToDomain(characterEntity)
        }
    }

    //logic to return always a list, but with error or success wrapper
    override suspend fun retrieveFilms(filmIds: List<Int>): ResultWrapper<List<Film>> {
        var hasError = false
        try {
            withContext(Dispatchers.IO) {
                val filmDeferreds = mutableListOf<Deferred<FilmDetailResponse>>()
                filmIds.forEach { id ->
                    val filmDetailDeferred = async {
                        starWarsApi.getFilmDetail(id)
                    }
                    filmDeferreds.add(filmDetailDeferred)
                }
                val films = networkMapper.mapFilmResponsesToDomain(filmDeferreds.map { it.await() })
                starWarsDatabase.withTransaction {
                    starWarsDatabase.getFilmDao().insertAllFilms(dbMapper.mapFilmDomainToEntity(films))
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            hasError = true
        } finally {
            //list from single source of truth
            val list = mutableListOf<Film>()
            filmIds.forEach { id ->
                val filmFound = starWarsDatabase.getFilmDao().getFilmById(id)
                if(filmFound != null)
                    list.add(dbMapper.mapFilmEntityToDomain(filmFound))
            }
            return if(hasError)
                ResultWrapper.Error(list)
            else
                ResultWrapper.Success(list)
        }
    }

    override suspend fun retrieveVehicles(vehicleIds: List<Int>): ResultWrapper<List<Vehicle>> {
        var hasError = false
        try {
            withContext(Dispatchers.IO) {
                val vehicleDeferreds = mutableListOf<Deferred<VehicleDetailResponse>>()
                vehicleIds.forEach { id ->
                    val vehicleDetailDeferred = async {
                        starWarsApi.getVehicleDetail(id)
                    }
                    vehicleDeferreds.add(vehicleDetailDeferred)
                }
                val vehicles = networkMapper.mapVehicleResponsesToDomain(vehicleDeferreds.map { it.await() })
                starWarsDatabase.withTransaction {
                    starWarsDatabase.getVehicleDao().insertAllVehicles(dbMapper.mapVehicleDomainToEntity(vehicles))
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            hasError = true
        } finally {
            //list from single source of truth
            val list = mutableListOf<Vehicle>()
            vehicleIds.forEach { id ->
                val vehicleFound = starWarsDatabase.getVehicleDao().getVehicleById(id)
                if(vehicleFound != null)
                    list.add(dbMapper.mapVehicleEntityToDomain(vehicleFound))
            }
            return if(hasError)
                ResultWrapper.Error(list)
            else
                ResultWrapper.Success(list)
        }

    }
}