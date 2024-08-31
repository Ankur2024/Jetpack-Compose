package com.example.marsphoto.data

import com.example.marsphoto.network.MarsApi
import com.example.marsphoto.network.MarsApiService
import com.example.marsphoto.network.MarsPhoto

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}