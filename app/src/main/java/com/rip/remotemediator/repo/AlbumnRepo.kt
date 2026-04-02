package com.rip.remotemediator.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rip.remotemediator.AlbumViewModel
import com.rip.remotemediator.local.AlbumDao
import com.rip.remotemediator.local.AlbumModel
import com.rip.remotemediator.remote.AlbumService
import kotlinx.coroutines.flow.Flow

class AlbumnRepo(val service: AlbumService, val dao: AlbumDao,val vm: AlbumViewModel) {

    @OptIn(ExperimentalPagingApi::class)
     fun getAlbums(): Flow<PagingData<AlbumModel>> {
        val config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 40,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(config, remoteMediator = AlbumMediator(service, dao,vm)){
            dao.getAlbums()
        }.flow
        }
    }
