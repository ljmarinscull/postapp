package com.lj.postapp.data

import com.lj.postapp.data.model.CommentObject
import com.lj.postapp.data.model.PostObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IBackendService {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostObject>>

    @GET("comments")
    suspend fun getPostCommentsById(@Query("q") query: String): Response<List<CommentObject>>
}