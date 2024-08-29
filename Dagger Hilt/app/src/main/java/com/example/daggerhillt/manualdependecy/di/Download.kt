package com.example.daggerhillt.manualdependecy.di

import android.util.Log

class HttpClient{

}

class Executor{

}

class Request constructor(private val client: HttpClient, private val executor: Executor){
    fun getRequest(){
        Log.d("main","File Downloading")
    }
}

class Downloader constructor(private val request: Request){
    fun getDownload(){
        request.getRequest()
    }
}

object DownloadFactory{
    fun create(): Downloader{
        val client = HttpClient()
        val executor = Executor()
        val request = Request(client, executor)
        return Downloader(request)
    }
}