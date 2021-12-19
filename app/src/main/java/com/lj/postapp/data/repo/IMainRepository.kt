package com.lj.postapp.data.repo

import com.lj.postapp.data.model.CommentObject
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.utils.Result

interface IMainRepository {
    suspend fun getPosts() : Result<List<PostObject>>
    suspend fun getPostCommentsById(id: Int) : Result<List<CommentObject>>
}
