package com.lj.postapp.ui.postcomments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.postapp.data.model.CommentObject
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.data.repo.IMainRepository
import com.lj.postapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCommentsViewModel
@Inject constructor(
    private val mainRepository: IMainRepository,
) : ViewModel() {

    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private var _comments = MutableLiveData<List<CommentObject>>()
    val comments: LiveData<List<CommentObject>> = _comments

    fun getCommentsByPostId(postId: Int){

        _progressBarVisible.value = true
        viewModelScope.launch(Dispatchers.Main) {

            val result = Result.Success(listOf(
                CommentObject(
                    postId = -1,
                    id = -1,
                    name = "Marin",
                    email = "ljmarin@gmail.com",
                    body = "Hola mundo , marin"
                )

            ))
            //mainRepository.getPostCommentsById(postId)

            _progressBarVisible.value = false
            _comments.value = result.data!!

            /*   when (result) {
                   is Result.Success -> {
                       _posts.value = result.data!!
                   }
                   is Result.Error -> {
                       _posts.value = emptyList()
                   }
               }*/
        }
    }
}