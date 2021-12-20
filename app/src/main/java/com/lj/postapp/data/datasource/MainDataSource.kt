package com.lj.postapp.data.datasource

import com.lj.postapp.data.IBackendService
import com.lj.postapp.data.model.CommentObject
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.utils.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.IOException
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
                            return@withContext Result.Error(IOException("No se han encontrado posts."), result.code())
                    }
                    return@withContext Result.Error(IOException("Ha ocurrido un error al obtener los posts."), result.code())
                }
                else -> {
                    return@withContext Result.Error(IOException("Ha ocurrido un error al obtener los posts."), result.code())
                }
            }
        } catch(e: Exception){
            return@withContext Result.Error(e,1000)
        }
    }

    override suspend fun getPostCommentsById(id: Int): Result<List<CommentObject>> = withContext(IO) {

        try {
            val result = retrofit.getPostCommentsById(id.toString())

            when {
                result.code() == 200 -> {
                    if(result.body() != null) {
                        if (result.body()!!.isNotEmpty())
                            return@withContext Result.Success(result.body()!!)
                        else
                            return@withContext Result.Error(IOException("No se han encontrado comentarios para el post seleccionando."), result.code())
                    }
                    return@withContext Result.Error(IOException("Error al obtener los comentarios del post seleccionando."), result.code())
                }
                else -> {
                    return@withContext Result.Error(IOException("Error al obtener los comentarios del post seleccionando."), result.code())
                }
            }
        } catch(e: Exception){
            return@withContext Result.Error(e,1000)
        }
    }
}