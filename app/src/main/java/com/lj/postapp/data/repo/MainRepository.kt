package com.lj.postapp.data.repo

import com.lj.postapp.data.datasource.IMainDataSource
import com.lj.postapp.data.model.CommentObject
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.utils.Result
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val mainDataSource: IMainDataSource
        ) : IMainRepository {

    override suspend fun getPosts(): Result<List<PostObject>> = mainDataSource.getPosts()

    override suspend fun getPostCommentsById(id: Int): Result<List<CommentObject>> = mainDataSource.getPostCommentsById(id)
}