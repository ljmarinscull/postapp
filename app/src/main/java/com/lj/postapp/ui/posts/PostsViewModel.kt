package com.lj.postapp.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lj.postapp.data.model.PostObject
import com.lj.postapp.data.repo.IMainRepository
import com.lj.postapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel
@Inject constructor(
    private val mainRepository: IMainRepository,
) : ViewModel() {

    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private var _posts = MutableLiveData<List<PostObject>>()
    val posts: LiveData<List<PostObject>> = _posts

    fun getPosts(){
        _progressBarVisible.value = true
        viewModelScope.launch(Dispatchers.Main) {

            val result = mainRepository.getPosts()

            _progressBarVisible.value = false
               when (result) {
                   is Result.Success -> {
                       _posts.value = result.data!!
                   }
                   is Result.Error -> {
                       _posts.value = emptyList()
                   }
               }
        }
    }
}