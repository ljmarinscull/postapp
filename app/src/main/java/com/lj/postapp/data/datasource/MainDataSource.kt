package com.lj.postapp.data.datasource

import com.lj.postapp.data.model.CommentObject
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.utils.Result
import javax.inject.Inject

class MainDataSource @Inject constructor(): IMainDataSource {

    override suspend fun getPosts(): Result<List<PostObject>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostCommentsById(id: Int): Result<List<CommentObject>> {
        TODO("Not yet implemented")
    }

}