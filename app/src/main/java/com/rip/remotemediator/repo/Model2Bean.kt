package com.rip.remotemediator.repo

import com.rip.remotemediator.local.AlbumModel
import com.rip.remotemediator.remote.AlbumBean

class Model2Bean: DataMaper<AlbumModel, AlbumBean> {

    override fun map(input: AlbumModel): AlbumBean {
        return AlbumBean(input.id, input.userId, input.title, input.imgUrl)
    }
}