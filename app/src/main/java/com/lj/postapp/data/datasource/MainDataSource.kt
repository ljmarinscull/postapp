package com.lj.postapp.data.datasource

import com.lj.postapp.data.IBackendService
import com.lj.postapp.data.model.CommentObject
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.utils.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class MainDataSource @Inject constructor(
    private val retrofit: IBackendService
): IMainDataSource {

    override suspend fun getPosts(): Result<List<PostObject>> = withContext(IO) {
        try {
            val result = retrofit.getPosts()

            when {
                result.code() == 200 -> {
                    if(result.body() != null) {
                        if (result.body()!!.isNotEmpty())
                            return@withContext Result.Success(result.body()!!)
                        else
                            return@withContext Result.Error(IOException("No se ha encontrado el html del formulario."), result.code())
                    }
                    return@withContext Result.Error(IOException("No se ha encontrado el html del formulario."), result.code())
                }
                else -> {
                    return@withContext Result.Error(IOException("Error al obtener los posts."), result.code())
                }
            }
        } catch(e: Exception){
            return@withContext Result.Error(IOException(e.localizedMessage),1000)
        }

    }

        override suspend fun getPostCommentsById(id: Int): Result<List<CommentObject>> = withContext(IO) {
            val result = retrofit.getPostCommentsById(id.toString())
            return@withContext Result.Success(emptyList())
        }

    }