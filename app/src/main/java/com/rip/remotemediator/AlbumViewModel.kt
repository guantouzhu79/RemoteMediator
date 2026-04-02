package com.rip.remotemediator

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rip.remotemediator.local.AlbumDao
import com.rip.remotemediator.local.MyDatabase
import com.rip.remotemediator.remote.AlbumService
import com.rip.remotemediator.remote.RetrofitClient
import com.rip.remotemediator.repo.AlbumnRepo
import kotlinx.coroutines.flow.catch

class AlbumViewModel(val context: Application) : AndroidViewModel(context) {

    val service: AlbumService by lazy {
        RetrofitClient.getService(AlbumService::class.java)
    }
    val dao: AlbumDao by lazy {
        MyDatabase.getInstance(context).getAlbumDao()
    }
    val albumData by lazy {
        AlbumnRepo(service, dao,this).getAlbums().catch {
            it.printStackTrace()
        }.cachedIn(viewModelScope)
    }

    fun getStartPage(): Int {
        return getApplication< Application>().getSharedPreferences("Config",
            Context.MODE_PRIVATE).getInt("StartPage", 1)
    }

    fun setStartPage(page: Int) {
        getApplication< Application>().getSharedPreferences("Config",
            Context.MODE_PRIVATE).edit{
                putInt("StartPage", page)
        }
    }

}



