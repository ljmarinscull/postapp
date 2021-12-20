package com.lj.postapp.ui.postcomments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.postapp.data.model.CommentObject
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

            val result = mainRepository.getPostCommentsById(postId)
            _progressBarVisible.value = false

            when (result) {
                   is Result.Success -> {
                       _comments.value = result.data!!
                   }
                   is Result.Error -> {
                       _comments.value = emptyList()
                   }
               }
        }
    }
}