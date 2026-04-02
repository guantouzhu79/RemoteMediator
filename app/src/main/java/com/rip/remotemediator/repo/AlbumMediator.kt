package com.rip.remotemediator.repo

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.rip.remotemediator.AlbumViewModel
import com.rip.remotemediator.local.AlbumDao
import com.rip.remotemediator.local.AlbumModel
import com.rip.remotemediator.remote.AlbumService

@OptIn(ExperimentalPagingApi::class)
class AlbumMediator(val service: AlbumService, val dao: AlbumDao, val vm: AlbumViewModel) :
    RemoteMediator<Int, AlbumModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AlbumModel>
    ): MediatorResult {

        var endOfPaginationReached = true

        try {
            val nextKey = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    Log.d("chuchu", "run *************${lastItem}")
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true)
                    }

                    lastItem.id /state.config.pageSize + 1
                }
            }

            Log.d("chuchu", "run *************${nextKey}")
            var loadSize = state.config.pageSize
            if (loadType == LoadType.REFRESH){
                loadSize = state.config.initialLoadSize
            }
            val response = service.getAlbums(
                mapOf(
                    "_page" to nextKey.toString(),
                    "_limit" to loadSize.toString()
                )
            )

            if (response.isSuccessful) {
                val data = response.body()

                    data?.map {
                    AlbumModel(
                        id = it.id,
                        userId = it.userId,
                        title = it.title,
                        imgUrl = "https://n.sinaimg.cn/spider20260228/245/w600h445/20260228/6ca1-cc1eab0c9513b8926eee976dafa97fd8.png",
                        page = (it.id -1) / state.config.pageSize+1 ,
                        createTime = ""
                    )
                }?.let {

                    if (loadType == LoadType.REFRESH) {
                        dao.refreshAlbums( it)

                    }
                    if (loadType == LoadType.APPEND) {
                         dao.insertOrUpdate(it)

                    }

                }
                endOfPaginationReached = data.isNullOrEmpty()

            } else {
                throw Exception("${response.code()}&${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }

        return MediatorResult.Success(
            endOfPaginationReached = endOfPaginationReached
        )
    }

//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.SKIP_INITIAL_REFRESH
//    }
}

suspend fun test() {
//    Log.d("chuchu", "run *******${loadType}")
//    var response: Response<List<AlbumBean>>?
//    try {
//        var startPage = vm.getStartPage()
//        var pageSize = state.config.pageSize
//
//        if (loadType == LoadType.REFRESH) {
//            Log.d("chuchu", "REFRESH")
//            startPage = 1
//            pageSize = state.config.initialLoadSize
//        }
//
//        response = service.getAlbums(
//            mapOf(
//                "_page" to startPage.toString(),
//                "_limit" to pageSize.toString()
//            )
//        )
//        if (response.isSuccessful) {
//            val data = response.body()
//            data?.map {
//                AlbumModel(
//                    id = it.id,
//                    userId = it.userId,
//                    title = it.title,
//                    imgUrl = "https://n.sinaimg.cn/spider20260228/245/w600h445/20260228/6ca1-cc1eab0c9513b8926eee976dafa97fd8.png",
//                    createTime = ""
//                )
//            }?.let {
//
//                if (it.size > 0) {
//                    if (loadType == LoadType.REFRESH) {
//                        dao.clearAlbums()
//                    }
//                    val result = dao.insertOrUpdate(it)
//                    vm.setStartPage(
//                        result.size / state.config
//                            .pageSize + 1
//                    )
//
//
//                }
//
//            }
//
//
//        } else {
//            throw Exception("${response.code()}&${response.message()}")
//        }
//
//
//    } catch (e: Exception) {
//        e.printStackTrace()
//        return MediatorResult.Error(e)
//    }
//    Log.d("chuchu", "run *******${response.body() == null || response.body()!!
//        .isEmpty()}")
//    return MediatorResult.Success(
//        endOfPaginationReached = (response.body() == null || response.body()!!
//            .isEmpty())
//    )
}